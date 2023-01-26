package com.example.firstCamundaTask.task;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.service.EmailMessage;
import com.example.firstCamundaTask.service.JiraServiceAPI;
import com.example.firstCamundaTask.service.SendEmailService;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendEmailToEmployeeTask")
public class SendEmailToEmployeeTask implements JavaDelegate {
    private final JiraServiceAPI jiraService;

    public SendEmailToEmployeeTask(JiraServiceAPI jiraService){
        this.jiraService = jiraService;
    }

    @Override
    public void execute(DelegateExecution execution)  {
        log.info("sending only to employee");
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        SendEmailService sendEmailService = new SendEmailService();
        EmailMessage emailMessage = new EmailMessage(jiraService);
        String message = emailMessage.setMessageEmployee(jiraIssue) ;
        sendEmailService.send(jiraIssue.getEmail(), message);
    }
}

