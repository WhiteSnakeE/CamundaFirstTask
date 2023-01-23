package com.example.firstCamundaTask.task;


import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetAllIssuesTask")
@ProcessApplication("applicationRules")
public class GetAllIssuesTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        JiraRequest jiraRequest = new JiraRequest();
        List<JiraIssue> allIssuies = jiraRequest.getIssuesFields();
        boolean areAllIssuesNull = jiraRequest.areNeedIssuePresents();
        log.info("All Issues - {}",allIssuies);
        ProcessEnv processEnv = new ProcessEnv(execution);
        processEnv.setAreNeedIssuesPresent(areAllIssuesNull);
        processEnv.setJiraIssues(allIssuies);

    }

}
