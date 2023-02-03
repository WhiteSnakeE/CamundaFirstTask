package com.example.issueRemindEmailSender.repository;

import com.example.issueRemindEmailSender.model.JiraIssue;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailMessageRepository {


    String setMessageEmployee(JiraIssue jiraIssue);

    String setMessageBoss(JiraIssue jiraIssue);


}
