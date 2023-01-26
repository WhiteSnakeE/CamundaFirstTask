package com.example.firstCamundaTask.task;
import com.example.firstCamundaTask.ProcessEnv;
import com.example.firstCamundaTask.model.JiraIssue;
import com.example.firstCamundaTask.service.JiraServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CalcDeltaTask")
public class CalcDeltaTask implements JavaDelegate {

    private final JiraServiceAPI jiraRequest;

    public CalcDeltaTask(JiraServiceAPI jiraRequest) {
        this.jiraRequest = jiraRequest;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        int days = jiraRequest.lastUpdateDays(jiraIssue);
        processEnv.setIsDeltaEqualsOrMoreThanFive(days);
        processEnv.setIsDeltaEqualsOrMoreThanTen(days);
        log.info("Delta is {}",days);

    }
}
