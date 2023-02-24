package com.example.issueRemindEmailSender.repository;

import com.example.issueRemindEmailSender.configuration.SendEmailConfiguration;
import com.example.issueRemindEmailSender.service.SendEmailService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Getter
@Slf4j
public class SenEmailRepositoryImpl implements SendEmailRepository {
    public static String sendMessageText;

    private final SendEmailConfiguration sendEmailConfiguration;

    public SenEmailRepositoryImpl(SendEmailConfiguration sendEmailConfiguration) {
        this.sendEmailConfiguration = sendEmailConfiguration;
    }


    @Override
    public boolean send(String receiveEmail, String messageText) {
        sendMessageText = messageText;
        Session session = sendEmailConfiguration.getSession();
        Message message = prepareMessage(session, sendEmailConfiguration.getEmail(), receiveEmail);
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
            message.setSubject("Employee issues confirmation");
            message.setText(sendMessageText);
            return message;

        } catch (Exception ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
