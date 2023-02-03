package com.example.issueRemindEmailSender.task;
import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraServiceAPI;
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
    public void execute(DelegateExecution execution)  {
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        int days = jiraRequest.lastUpdateDays(jiraIssue);
        jiraIssue.setDelta(days);
        log.info("Delta is {}, {}",days,execution.getProcessInstance());

    }
}
