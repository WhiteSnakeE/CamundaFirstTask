package com.example.issueRemindEmailSender.task;


import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.service.EmailMessage;


import com.example.issueRemindEmailSender.service.SendEmailService;
import com.example.issueRemindEmailSender.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailToTeamLead")
public class SendEmailToTeamLeadTask implements JavaDelegate {




    @Override
    public void execute(DelegateExecution execution)  {
        log.info("sending to team lead");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        SendEmailService sendEmailService = new SendEmailService();
        EmailMessage emailMessage = new EmailMessage();
        String messageToBoss = emailMessage.setMessageBoss(jiraIssue);
        sendEmailService.send(processEnv.getEmail(),messageToBoss);
    }
}

