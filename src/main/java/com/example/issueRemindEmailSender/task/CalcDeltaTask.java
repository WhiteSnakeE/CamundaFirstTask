package com.example.issueRemindEmailSender.task;
import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;

import com.example.issueRemindEmailSender.service.JiraService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CalcDelta")
public class CalcDeltaTask implements JavaDelegate {



    @Override
    public void execute(DelegateExecution execution)  {
        ProcessEnv processEnv = new ProcessEnv(execution);
        JiraIssue jiraIssue = processEnv.getJiraIssues();
        int days = JiraService.lastUpdateDays(jiraIssue);
        jiraIssue.setDelta(days);
        processEnv.setJiraIssues(jiraIssue);
        log.info("Delta is {}, {}",days,execution.getProcessInstance());

    }
}
