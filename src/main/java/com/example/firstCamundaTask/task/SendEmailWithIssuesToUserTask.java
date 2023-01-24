package com.example.firstCamundaTask.task;

import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.configuration.EmailProperties;
import com.example.firstCamundaTask.emailSender.SendEmail;
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

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("sending only to employee");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        SendEmail sendEmail = new SendEmail();
        String message = "Hello, You have issues than not updated five days!";
        sendEmail.send(jiraIssue, jiraIssue.getEmail(), message);
    }


}

