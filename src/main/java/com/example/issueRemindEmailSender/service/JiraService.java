package com.example.issueRemindEmailSender.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.repository.JiraRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class JiraService {

    private final JiraRepository jiraRepository;

    private int maxResults = 500;
    private static final String JQL = "project = \"CamundaTraning\" and status!=done";


    public JiraService(JiraRepository jiraRepository) {
        this.jiraRepository = jiraRepository;
    }


    public List<JiraIssue> getIssuesFields() {
        SearchResult searchResult;
        try {
            searchResult = jiraRepository.getIssuesFields(JQL, maxResults);
            log.info("Jira taken successfully");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Jira was not taken");
            throw new BpmnError("SOLVIT_ERROR", e.getMessage());
        }
       ;
        Iterable<Issue> issues = searchResult.getIssues();
        List<JiraIssue> jiraIssues = new ArrayList<>();

            for (Issue issue : issues) {
                jiraIssues.add(JiraIssue.builder()
                        .id(issue.getId())
                        .createDate(issue.getCreationDate())
                        .updateDate(issue.getUpdateDate())
                        .statusName((issue.getStatus().getName()))
                        .email("Objects.requireNonNull(issue.getReporter()).getEmailAddress()").build());

        }

        return jiraIssues;

    }

    public static int lastUpdateDays(JiraIssue issue) {
        if (DateTime.now().getDayOfMonth() == issue.getUpdateDate().getDayOfMonth()) return 0;
        return (DateTime.now()
                .minusSeconds(issue.getUpdateDate().getSecondOfMinute())
                .minusMinutes(issue.getUpdateDate().getMinuteOfHour())
                .minusHours(issue.getUpdateDate().getHourOfDay())
                .minusDays(issue.getUpdateDate().getDayOfMonth()).getDayOfMonth());
    }

}
