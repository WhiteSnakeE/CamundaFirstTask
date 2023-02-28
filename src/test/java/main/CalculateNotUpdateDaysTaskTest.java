package main;


import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.task.CalculateNotUpdateDaysTask;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateNotUpdateDaysTaskTest {

	@Mock
	private DelegateExecution execution;

	@InjectMocks
	private CalculateNotUpdateDaysTask task;

	@Test
	public void givenJiraIssue_whenCalculateNotUpdateDays_thenSetDelta() throws IOException {

		//prepare
		JiraIssue jiraIssue = getJiraIssue();

		when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);
		//test
		task.execute(execution);

		//verify
		verify(execution).setVariable(ProcessEnv.ISSUE, jiraIssue);

	}


	private JiraIssue getJiraIssue() throws IOException {
		String object = IOUtils.resourceToString("/data/jiraIssue.json", Charset.defaultCharset());
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(object, JiraIssue.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Can not deserialize solvitCase", e);
		}
	}

}
