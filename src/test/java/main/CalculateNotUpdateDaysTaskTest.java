package main;


import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.task.CalculateNotUpdateDaysTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateNotUpdateDaysTaskTest {

	@Mock
	private DelegateExecution execution;

	@InjectMocks
	private CalculateNotUpdateDaysTask task;

	@Test
	public void testExecute() {

		//prepare

		JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-29T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
		int data = JiraService.lastUpdateDays(jiraIssue);
		JiraIssue jiraIssueExpected = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-29T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
		int data2 = JiraService.lastUpdateDays(jiraIssueExpected);
		jiraIssueExpected.setDelta(data2);

		when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);

		//test
		task.execute(execution);

		//verify
		verify(execution).setVariable(ProcessEnv.ISSUE, jiraIssueExpected);

	}

	@Test
	public void testExecute_2() {

		//prepare
		JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-30T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
		JiraIssue jiraIssueExpected = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-30T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
		jiraIssueExpected.setDelta(4);

		when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);

		//test
		task.execute(execution);

		//verify
		verify(execution).setVariable(ProcessEnv.ISSUE, jiraIssue);

	}

}
