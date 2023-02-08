package com.example.issueRemindEmailSender.service;

import com.example.issueRemindEmailSender.configuration.EmailProperties;
import com.example.issueRemindEmailSender.repository.SendEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
public class SendEmailService {

    public static String sendMessageText;

    private final SendEmailRepository sendEmailRepository;

    public SendEmailService(SendEmailRepository sendEmailRepository){
        this.sendEmailRepository =sendEmailRepository;
    }

    public void send(String receiveEmail, String messageText)  {
        EmailProperties emailProperties = new EmailProperties();
        sendMessageText = messageText;
        log.info("Email preparation to {}", receiveEmail);
        Session session = Session.getInstance(sendEmailRepository.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmailRepository.getEmail(), sendEmailRepository.getPassword());
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

    private Message prepareMessage(Session session, String myAccountEmail, String recipient) {
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

