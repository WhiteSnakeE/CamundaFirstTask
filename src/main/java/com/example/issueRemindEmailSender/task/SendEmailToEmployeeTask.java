package com.example.issueRemindEmailSender.task;
import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.service.EmailMessage;

import com.example.issueRemindEmailSender.service.SendEmailService;
import com.example.issueRemindEmailSender.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailToEmployee")
public class SendEmailToEmployeeTask implements JavaDelegate {

    private final SendEmailService sendEmailService;

    public SendEmailToEmployeeTask(SendEmailService sendEmailService){
        this.sendEmailService = sendEmailService;

    }
    @Override
    public void execute(DelegateExecution execution)  {
        log.info("sending only to employee");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        String message = EmailMessage.setMessageEmployee(jiraIssue);
        sendEmailService.send(jiraIssue.getEmail(), message);

    }
}

