package com.example.issueRemindEmailSender.repository;

import com.example.issueRemindEmailSender.model.JiraIssue;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
public interface SendEmailRepository {


    String getEmail();

    String getPassword();

    Properties getProperties();


}