package com.example.firstCamundaTask.dmn;

import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.model.JiraIssue;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@ProcessApplication("applicationRules")
@Component("CheckIssues")
public class EmailRules extends ServletProcessApplication {


    public void evaluateDecisionTable(ProcessEngine processEngine) throws UnirestException {
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        InputStream inputStream = EmailRules.class.getResourceAsStream("applicationRules.dmn");
        DmnDecision decision = dmnEngine.parseDecision("EmailRules", inputStream);
        JiraRequest jiraRequest = new JiraRequest();
        List<JiraIssue> allIssuies = jiraRequest.getIssuesFields();
        JiraIssue jiraIssue = allIssuies.get(0);
        System.out.println(" dfdf" + jiraIssue.getStatusName());
        VariableMap variables = Variables.createVariables()
                .putValue(jiraIssue.getStatusName(), "In Progress")
                .putValue("day", 10);

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

//        DecisionService decisionService = processEngine.getDecisionService();
//        JiraRequest jiraRequest = new JiraRequest();
//        List<JiraIssue> allIssuies = jiraRequest.getIssuesFields();
//        JiraIssue jiraIssue = allIssuies.get(0);
//        System.out.println(" dfdf" + jiraIssue.getStatusName());
//        VariableMap variables = Variables.createVariables()
//                .putValue(jiraIssue.getStatusName(), "In Progress")
//                .putValue("day", 10);
//
//        DmnDecisionTableResult dishDecisionResult = decisionService.evaluateDecisionTableByKey("EmailRules", variables);
//        String desiredDish = dishDecisionResult.getSingleEntry();
//        System.out.println(desiredDish);
//        DecisionsEvaluationBuilder a = decisionService.evaluateDecisionByKey("dish");




    }
}

