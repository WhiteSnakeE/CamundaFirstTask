package com.example.issueRemindEmailSender.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.repository.JiraRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class JiraService {

    @Value("${project}")
    private String defaultProject;

    private String project;

    private final JiraRepository jiraRepository;

    private int maxResults = 500;

    private String jql = "\"project = \"" + project + "\" and status!=done\"";

    public JiraService(JiraRepository jiraRepository) {
        this.jiraRepository = jiraRepository;
    }


    public List<JiraIssue> getIssuesFields() {
        SearchResult searchResult;
        System.out.println(jql);
        searchResult = jiraRepository.getIssuesFields(jql, maxResults);
        log.info("Jira taken successfully");


        Iterable<Issue> issues = searchResult.getIssues();
        List<JiraIssue> jiraIssues = new ArrayList<>();

        for (Issue issue : issues) {
            jiraIssues.add(JiraIssue.builder()
                    .self(issue.getSelf().toString())
                    .createDate(issue.getCreationDate())
                    .updateDate(issue.getUpdateDate())
                    .statusName((issue.getStatus().getName()))
                    .email(Objects.requireNonNull(issue.getReporter()).getEmailAddress()).build());
        }

        return jiraIssues;

    }


    public void setJQLProject(String projectName){
        if(projectName!=null) jql =  "project = \"" + projectName + "\" and status!=done";
        else jql =  "project = \"" + project + "\" and status!=done";
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
