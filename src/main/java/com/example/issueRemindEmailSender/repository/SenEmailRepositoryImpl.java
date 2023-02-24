package com.example.issueRemindEmailSender.repository;

import com.example.issueRemindEmailSender.configuration.SendEmailConfiguration;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.mail.Session;

@Repository
@Getter
public class SenEmailRepositoryImpl implements SendEmailRepository{

    private final SendEmailConfiguration sendEmailConfiguration;

    public SenEmailRepositoryImpl (SendEmailConfiguration sendEmailConfiguration){
        this.sendEmailConfiguration = sendEmailConfiguration;
    }

    @Override
    public Session getSession() {
        return sendEmailConfiguration.getSession();
    }
    @Override
    public String getEmail() {
        return sendEmailConfiguration.getEmail();
    }
}
