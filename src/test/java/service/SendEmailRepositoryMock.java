package service;

import com.example.issueRemindEmailSender.repository.SendEmailRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
@Profile({"dev"})
public class SendEmailRepositoryMock implements SendEmailRepository {
    @Override
    public String getEmail() {
        return "vlad.kharchenko2003@gmail.com";
    }

    @Override
    public String getPassword() {
        return "";
    }

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
