package com.example.issueRemindEmailSender.service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.repository.JiraRepositoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JiraServiceAPI {

    private final JiraRepositoryRequest jiraRepositoryRequest;


    public JiraServiceAPI( JiraRepositoryRequest jiraRepositoryRequest) {
        this.jiraRepositoryRequest = jiraRepositoryRequest;
    }

    public List<JiraIssue> getIssuesFields()  {
        int countOfIssues = jiraRepositoryRequest.getTotalIssues();
        List<JiraIssue> issueList  = new ArrayList<>(countOfIssues);
        for (int i = 0; i < countOfIssues; i++) {
            String id =  jiraRepositoryRequest.getId(i);
            String createdDate = jiraRepositoryRequest.getCreateDate(i);
            String updatedDate = jiraRepositoryRequest.getUpdateDate(i);
            String email = jiraRepositoryRequest.getEmail(i);
            String status =jiraRepositoryRequest.getStatus(i);
            issueList.add(JiraIssue.builder().id(Long.valueOf(id)).createDate(DateTime.parse(createdDate))
                    .updateDate(DateTime.parse(updatedDate)).email(email).statusName(status).build());
        }
        return issueList;
    }

    public boolean areNeedIssuePresents(List<JiraIssue> allIssuies)  {
        return !allIssuies.isEmpty();
    }

    public static int lastUpdateDays(JiraIssue issue){
        if(DateTime.now().getDayOfMonth()==issue.getUpdateDate().getDayOfMonth()) return 0;
        return (DateTime.now()
                .minusSeconds(issue.getUpdateDate().getSecondOfMinute())
                .minusMinutes(issue.getUpdateDate().getMinuteOfHour())
                .minusHours(issue.getUpdateDate().getHourOfDay())
                .minusDays(issue.getUpdateDate().getDayOfMonth()).getDayOfMonth());
    }
}
