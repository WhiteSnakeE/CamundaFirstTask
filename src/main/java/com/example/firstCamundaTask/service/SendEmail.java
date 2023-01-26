package com.example.firstCamundaTask.service;

import com.example.firstCamundaTask.configuration.EmailProperties;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class SendEmail {

    public static Long id;
    public static DateTime updateDate;
    public static DateTime createDate;
    public static String email;
    public static String statusName;
    public static String sendMessageText;


    public void send(JiraIssue jiraIssue,String receiveEmail, String messageText)  {
        EmailProperties emailProperties = new EmailProperties();
        id = jiraIssue.getId();
        updateDate = jiraIssue.getUpdateDate();
        createDate = jiraIssue.getCreateDate();
        email = receiveEmail;
        statusName = jiraIssue.getStatusName();
        sendMessageText = messageText;
        log.info("Email preparation to {}", email);
        Session session = Session.getInstance(emailProperties.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("surtx0119@gmail.com", "kqjiymrkjdprqbib");
            }
        });

        Message message = prepareMessage(session, "surtx0119@gmail.com", email);

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
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    }

