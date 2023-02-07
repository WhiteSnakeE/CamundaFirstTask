package com.example.issueRemindEmailSender.task;



import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import lombok.extern.slf4j.Slf4j;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetAllIssues")
public class GetAllIssuesTask implements JavaDelegate {

    private final JiraService jiraService;

    public GetAllIssuesTask(JiraService jiraService){
        this.jiraService = jiraService;
    }

    @Override
    public void execute(DelegateExecution execution)  {
        List<JiraIssue> allIssuies = jiraService.getIssuesFields();
        boolean areAllIssuesNull = jiraService.areNeedIssuePresents(allIssuies);
        log.info("All Issues - {}", allIssuies);
        ProcessEnv processEnv = new ProcessEnv(execution);
        processEnv.setAreNeedIssuesPresent(areAllIssuesNull);
        processEnv.setJiraIssues(allIssuies);

    }

}
