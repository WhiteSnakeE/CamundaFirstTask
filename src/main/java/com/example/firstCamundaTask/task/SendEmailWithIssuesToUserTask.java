package com.example.firstCamundaTask.task;

import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.configuration.EmailProperties;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Component("SendEmailWithIssuesToUserTask")
public class SendEmailWithIssuesToUserTask implements JavaDelegate {
    @Autowired
   public EmailProperties emailProperties;

    public static Integer id;
    public static DateTime dateTime;
    public static String statusName;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String email = "vlad.kharchenko2003@gmail.com";
        log.info("Email preparation to {}",email);
        Session session = Session.getInstance(emailProperties.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getEmail(), emailProperties.getPassword());
            }
        });
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraRequest jiraRequest = new JiraRequest();
        JiraIssue jiraIssue = processEnv.getJiraIssues();
            id = jiraIssue.getId();
            dateTime = jiraIssue.getDate();
            statusName = jiraIssue.getStatusName();
            Message message = prepareMessage(session,emailProperties.getEmail(), email);
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
            message.setText("Hello, You have overdue issues!" + "\n" +
                    "Id: " + id + "\n" +
                    "Last Update Time: " + dateTime + "\n" +
                    "Status Name: " + statusName );

            return message;

        } catch (Exception ex) {
            Logger.getLogger(SendEmailWithIssuesToUserTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

