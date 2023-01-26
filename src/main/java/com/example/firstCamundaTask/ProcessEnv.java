package com.example.firstCamundaTask;
import com.example.firstCamundaTask.model.JiraIssue;
import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.List;

public class ProcessEnv {

	// main process

	public static final String ARE_NEED_ISSUES_PRESENT = "areNeedIssueIsPresent";
	public static final String JIRA_ISSUES = "jiraIssues";
	public static final String ISSUE = "issue";
	public static final String DELTA_EQUALS_OR_MORE_THAN_FIVE = "deltaEqualsOrMoreThenFive";
	public static final String DELTA_EQUALS_OR_MORE_THAN_TEN = "deltaEqualsOrMoreThenTen";
	public static final String EMAIL = "email";

	private final VariableScope variableScope;


	public ProcessEnv(VariableScope variableScope) {
		this.variableScope = variableScope;
	}


	public void setEmail(String email) {
		variableScope.setVariable(EMAIL,email);
	}
	public String getEmail() {
		return (String) variableScope.getVariable(EMAIL);
	}
	public void setAreNeedIssuesPresent(boolean isSubTaskWithClonePresent) {
		variableScope.setVariable(ARE_NEED_ISSUES_PRESENT, isSubTaskWithClonePresent);
	}
	public void setIsDeltaEqualsOrMoreThanFive(int deltaEqualsOrMoreThanFive){
		variableScope.setVariable(DELTA_EQUALS_OR_MORE_THAN_FIVE,deltaEqualsOrMoreThanFive);
	}
	public void setIsDeltaEqualsOrMoreThanTen(int deltaEqualsOrMoreThanTen){
		variableScope.setVariable(DELTA_EQUALS_OR_MORE_THAN_TEN,deltaEqualsOrMoreThanTen);
	}
	public JiraIssue getJiraIssues() {
		return (JiraIssue) variableScope.getVariable(ISSUE);
	}

	public void setJiraIssues(List<JiraIssue> jiraIssues) {
		variableScope.setVariable(JIRA_ISSUES, jiraIssues);
	}

}
