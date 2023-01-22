package com.example.firstCamundaTask.task;


import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.dmn.EmailRules;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.dmn.DecisionsEvaluationBuilder;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component("GetAllIssuesTask")
@ProcessApplication("applicationRules")
public class GetAllIssuesTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        JiraRequest jiraRequest = new JiraRequest();
        List<JiraIssue> allIssuies = jiraRequest.getIssuesFields();
        log.info("All Issues - {}",allIssuies);

    }

}
