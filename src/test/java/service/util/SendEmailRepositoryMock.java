package service.util;

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
    public static String sendMessageText;

    public String getEmail() {
        return "vlad.kharchenko2003@gmail.com";
    }


    public String getPassword() {
        return "cjxwuavkjnrfyrcj";
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
    public Session getSession() {
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getEmail(), getPassword());
            }
        });
    }

    @Override
    public boolean send(String receiveEmail, String messageText) {
        sendMessageText = messageText;
        Session session = getSession();
        Message message = prepareMessage(session, getEmail(), receiveEmail);
        System.out.println(getEmail());
        System.out.println(receiveEmail);
        try {
            Transport.send(message);
            log.info("Message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("Message was not send");
            return false;
        }
        return true;
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Issues confirmation");
            message.setText(sendMessageText);
            return message;

        } catch (Exception ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }


}
