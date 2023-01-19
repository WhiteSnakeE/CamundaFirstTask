package com.example.firstCamundaTask.task;


import com.example.firstCamundaTask.Jira.JiraRequest;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("GetAllIssuesTask")
public class GetAllIssuesTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        JiraRequest jiraRequest = new JiraRequest();
        String allIssuies = jiraRequest.sendRequest();
        log.info("All Issues - {}",allIssuies);
    }

}
