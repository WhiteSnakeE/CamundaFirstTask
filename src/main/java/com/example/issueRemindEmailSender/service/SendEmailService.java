package com.example.issueRemindEmailSender.service;

import com.example.issueRemindEmailSender.configuration.EmailProperties;
import com.example.issueRemindEmailSender.repository.SendEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
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
        this.sendEmailRepository = sendEmailRepository;
    }

    public void send(String receiveEmail, String messageText)  {
       // System.out.println("send start " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
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
           // System.out.println("Transport send start " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
            Transport.send(message);
          //  System.out.println("Transport send end " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
            log.info("Message sent successfully");
        } catch (MessagingException e) {

            e.printStackTrace();
            log.info("Message was not send");
            throw new BpmnError("SOLVIT_ERROR", e.getMessage());
        }
      //  System.out.println("send end " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient) {
      //  System.out.println("prepareMessage start " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Confirmation");
            message.setText(sendMessageText);
         //   System.out.println("prepareMessage end " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
            return message;

        } catch (Exception ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  System.out.println("prepareMessage end NULL " + new DateTime().getSecondOfMinute() + "." + new DateTime().getMillisOfSecond() );
        return null;
    }
    }

