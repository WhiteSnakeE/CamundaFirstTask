package com.example.firstCamundaTask.task;
import com.example.firstCamundaTask.Jira.JiraRequest;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.model.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CalcDeltaTask")
public class CalcDeltaTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        JiraRequest jiraRequest = new JiraRequest();
        int days = jiraRequest.lastUpdateDays(jiraIssue);
        processEnv.setIsDeltaEqualsOrMoreThanFive(days == 5);
        processEnv.setIsDeltaEqualsOrMoreThanTen(days >= 10);
        log.info("Delta is {}",days);

    }
}
