package main;


import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.task.GetAllIssuesTask;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAllIssuesTaskTest {

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private GetAllIssuesTask task;

    @Mock
    private JiraService jiraServiceAPI;


    @Test
    public void givenJiraIssues_whenGetJiraBoard_thenReturnJiraAttemptsCount() throws Exception {
        JiraIssue jiraIssue = getJiraIssue();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue);

        when(jiraServiceAPI.getIssuesFields()).thenReturn(jiraIssueList);
        when(execution.getVariable(ProcessEnv.JIRA_ATTEMPTS_COUNT)).thenReturn(0);

        //test
        task.execute(execution);

        //verify
        verify(execution).setVariable(ProcessEnv.JIRA_ISSUES,jiraIssueList);
        verify(execution).setVariable(ProcessEnv.ARE_NEED_ISSUES_PRESENT,!jiraIssueList.isEmpty());
        verify(execution).setVariable(ProcessEnv.JIRA_ATTEMPTS_COUNT,1);
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
