package com.example.issueRemindEmailSender.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
@Getter
public class SenEmailRepositoryImpl implements SendEmailRepository{

    @Value("${mail.username}")
    private String email = "surtx0119@gmail.com";
    @Value("${mail.password}")
    private String password = "";

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return properties;
    }
}
