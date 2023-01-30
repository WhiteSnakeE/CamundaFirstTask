package com.example.firstCamundaTask.repository;

import com.example.firstCamundaTask.model.JiraIssue;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
public interface EmailMessageRepository {


    String setMessageEmployee(JiraIssue jiraIssue);

    String setMessageBoss(JiraIssue jiraIssue);


}
