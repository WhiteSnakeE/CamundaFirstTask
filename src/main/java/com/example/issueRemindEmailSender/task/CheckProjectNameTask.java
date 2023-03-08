package com.example.issueRemindEmailSender.task;

import com.example.issueRemindEmailSender.service.JiraService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CheckProjectName")
public class CheckProjectNameTask implements JavaDelegate {

    private final JiraService jiraService;

    public CheckProjectNameTask(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String project = (String) delegateExecution.getVariable("project");
        log.info("Project name is {}",project);
        jiraService.setJQLProject(project);
    }
}
