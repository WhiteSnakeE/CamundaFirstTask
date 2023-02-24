package com.example.issueRemindEmailSender.repository;

import com.example.issueRemindEmailSender.model.JiraIssue;
import org.springframework.stereotype.Repository;

import javax.mail.Session;
import java.util.Properties;

@Repository
public interface SendEmailRepository {



    Session getSession();

    String getEmail();


}
