package com.example.issueRemindEmailSender;

import com.example.issueRemindEmailSender.model.JiraIssue;
import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.List;

public class ProcessEnv {

    // main process

    public static final String ARE_NEED_ISSUES_PRESENT = "areNeedIssueIsPresent";
    public static final String JIRA_ISSUES = "jiraIssues";
    public static final String ISSUE = "issue";
    public static final String EMAIL = "email";
    public static final String JIRA_ATTEMPTS_COUNT = "jiraAttemptsCount";

    private final VariableScope variableScope;


    public ProcessEnv(VariableScope variableScope) {
        this.variableScope = variableScope;
    }


    public String getEmail() {

       // return (String) variableScope.getVariable(EMAIL);
        return "fdfdfdf";
    }

    public void setAreNeedIssuesPresent(boolean areNeedIssueIsPresent) {
        variableScope.setVariable(ARE_NEED_ISSUES_PRESENT, areNeedIssueIsPresent);
    }

    public void setJiraIssues(JiraIssue jiraIssues) {
        variableScope.setVariable(ISSUE, jiraIssues);
    }

    public JiraIssue getJiraIssues() {
        return (JiraIssue) variableScope.getVariable(ISSUE);
    }

    public void setJiraIssues(List<JiraIssue> jiraIssues) {
        variableScope.setVariable(JIRA_ISSUES, jiraIssues);
    }

    public void setJiraAttemptsCount(int jiraAttemptsCount) {
        variableScope.setVariable(JIRA_ATTEMPTS_COUNT, jiraAttemptsCount);
    }

    public int getJiraAttemptsCount() {
        if (variableScope.getVariable(JIRA_ATTEMPTS_COUNT) == null) {
            setJiraAttemptsCount(0);
        }
        return (int) variableScope.getVariable(JIRA_ATTEMPTS_COUNT);
    }

}
