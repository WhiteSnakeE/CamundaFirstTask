package com.example.issueRemindEmailSender.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
@Getter
public class SenEmailRepositoryImpl implements SendEmailRepository{

    @Value("${mail.username}")
    private String email;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String mailSmtpAuth;
    @Value("${mail.smtp.starttls.enable}")
    private String mailSmtpStarttlsEnable;
    @Value("${mail.smtp.host}")
    private String mailSmtpHost;
    @Value("${mail.smtp.port}")
    private String mailSmtpPort;
    @Value("${mail.smtp.ssl.trust}")
    private String mailSmtpSslTrust;
    @Value("${mail.smtp.ssl.protocols}")
    private String mailSmtpSslProtocols;

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", mailSmtpAuth);
        properties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        properties.put("mail.smtp.host", mailSmtpHost);
        properties.put("mail.smtp.port", mailSmtpPort);
        properties.put("mail.smtp.ssl.trust", mailSmtpSslTrust);
        properties.put("mail.smtp.ssl.protocols", mailSmtpSslProtocols);
        return properties;
    }
}
