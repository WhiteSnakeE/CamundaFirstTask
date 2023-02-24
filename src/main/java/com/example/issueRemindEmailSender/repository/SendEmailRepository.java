package com.example.issueRemindEmailSender.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailRepository {


    boolean send(String receiveEmail, String messageText);

}
