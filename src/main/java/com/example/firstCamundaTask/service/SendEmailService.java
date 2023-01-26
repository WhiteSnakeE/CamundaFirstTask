package com.example.firstCamundaTask.service;

import com.example.firstCamundaTask.configuration.EmailProperties;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class SendEmailService {

    public static String sendMessageText;

    public void send(String receiveEmail, String messageText)  {
        EmailProperties emailProperties = new EmailProperties();
        sendMessageText = messageText;
        log.info("Email preparation to {}", receiveEmail);
        Session session = Session.getInstance(emailProperties.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getEmail(), emailProperties.getPassword());
            }
        });

        Message message = prepareMessage(session, emailProperties.getEmail(), receiveEmail);

        try {
            Transport.send(message);
            log.info("Message sent successfully");
        } catch (MessagingException e) {

            e.printStackTrace();
            log.info("Message was not send");
        }

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Confirmation");
            message.setText(sendMessageText);

            return message;

        } catch (Exception ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    }

