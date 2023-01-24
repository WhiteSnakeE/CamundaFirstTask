package com.example.firstCamundaTask.task;

import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.configuration.EmailProperties;
import com.example.firstCamundaTask.emailSender.SendEmail;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailOnlyToEmployeeTask")
public class SendEmailOnlyToEmployeeTask implements JavaDelegate {
    @Autowired
    public EmailProperties emailProperties;

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

