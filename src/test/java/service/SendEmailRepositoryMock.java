package service;

import com.example.issueRemindEmailSender.repository.SendEmailRepository;
import com.example.issueRemindEmailSender.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile({"dev"})
@Slf4j
public class SendEmailRepositoryMock implements SendEmailRepository {


    public String getEmail() {
        return "vlad.kharchenko2003@gmail.com";
    }


    public String getPassword() {
        return "";
    }


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

    public boolean send(String receiveEmail, String messageText)  {
        return !receiveEmail.isEmpty() && !messageText.isEmpty();
    }


}
