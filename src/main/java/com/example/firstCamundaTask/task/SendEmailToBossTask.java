package com.example.firstCamundaTask.task;


import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.service.EmailMessage;

import com.example.firstCamundaTask.service.JiraServiceAPI;
import com.example.firstCamundaTask.service.SendEmail;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailToBossTask")
public class SendEmailToBossTask implements JavaDelegate {
    private final JiraServiceAPI jiraService;

    public SendEmailToBossTask(JiraServiceAPI jiraService){
        this.jiraService = jiraService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("sending to employee and boss");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        SendEmail sendEmail = new SendEmail();
        EmailMessage emailMessage = new EmailMessage(jiraService);
        String messageToBoss = emailMessage.setMessageBoss(jiraIssue);
        sendEmail.send(jiraIssue,processEnv.getEmail(),messageToBoss);
    }
}

