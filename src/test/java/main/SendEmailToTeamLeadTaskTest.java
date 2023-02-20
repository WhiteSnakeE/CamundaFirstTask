package main;

import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.SendEmailService;
import com.example.issueRemindEmailSender.task.SendEmailToTeamLeadTask;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SendEmailToTeamLeadTaskTest {
    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private SendEmailToTeamLeadTask task;
    @Mock
    private SendEmailService sendEmailService;

    @Test
    public void testExecute() throws IOException {

        //prepare
        JiraIssue jiraIssue = getJiraIssue();

        when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);
        when(execution.getVariable(ProcessEnv.EMAIL)).thenReturn("surtx0119@gmail.com");

        //test
        when(sendEmailService.send(any(),any())).thenReturn(true);
        task.execute(execution);


        //verify
         verify(execution).getVariable(ProcessEnv.ISSUE);
         verify(execution).getVariable(ProcessEnv.EMAIL);

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
