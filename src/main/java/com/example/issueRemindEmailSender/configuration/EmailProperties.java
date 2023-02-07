package com.example.issueRemindEmailSender.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

@Configuration
@Profile({"dev"})
@Getter
public class EmailProperties {
    @Value("${mail.username}")
    private String email = "surtx0119@gmail.com";
    @Value("${mail.password}")
    private String password = "cukwbvdjbzceujyo";

    public Properties getProperties(){
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
