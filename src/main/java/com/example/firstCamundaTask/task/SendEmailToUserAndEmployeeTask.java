package com.example.firstCamundaTask.task;


import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.emailSender.SendEmail;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailToUserAndEmployeeTask")
public class SendEmailToUserAndEmployeeTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("sending to employee and boss");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        SendEmail sendEmail = new SendEmail();
        String messageToEmpl = "Hello, You have issues than not updated ten days!";
        String messageToBoss = "Hello, this employee " + jiraIssue.getEmail() + "not updated issues for 10 days" ;
        sendEmail.send(jiraIssue, jiraIssue.getEmail(), messageToEmpl);
        sendEmail.send(jiraIssue,processEnv.getEmail(),messageToBoss);
    }
}

