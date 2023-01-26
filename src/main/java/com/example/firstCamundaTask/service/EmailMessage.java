package com.example.firstCamundaTask.service;

import com.example.firstCamundaTask.model.JiraIssue;
import com.example.firstCamundaTask.repository.EmailMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailMessage implements EmailMessageRepository {

    JiraServiceAPI jiraRequest;

    public EmailMessage(JiraServiceAPI jiraService){
        this.jiraRequest = jiraService;
    }

    @Override
    public String setMessageEmployee(JiraIssue jiraIssue){
        return "Hello, You have not been updating your issues for " + jiraRequest.lastUpdateDays(jiraIssue) +" days  \n" +
                "Issue id: " + jiraIssue.getId() + "\n" +
                "Issue start date: " + jiraIssue.getCreateDate() + "\n" +
                "Issue update date: " + jiraIssue.getUpdateDate() + "\n" +
                "Issue status: " + jiraIssue.getStatusName();
    }

    @Override
    public String setMessageBoss(JiraIssue jiraIssue){
        return "Hello, this employee " + jiraIssue.getEmail() +
                " has not been updating his issues for " + jiraRequest.lastUpdateDays(jiraIssue) +" days  \n" +
                "Issue id: " + jiraIssue.getId() + "\n" +
                "Issue start date: " + jiraIssue.getCreateDate() + "\n" +
                "Issue update date: " + jiraIssue.getUpdateDate() + "\n" +
                "Issue status: " + jiraIssue.getStatusName();
    }
}
