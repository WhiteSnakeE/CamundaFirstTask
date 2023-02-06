package main;


import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.task.SendEmailToEmployeeTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendEmailToEmployeeTaskTest {
    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private SendEmailToEmployeeTask task;

    @Test
    public void testExecute() {

        //prepare
        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-02-05T10:53:22.289+0200")).createDate(DateTime.parse("2023-02-04T10:53:18.127+0200")).email("gilent12345@gmail.com").statusName("To Do").build();

        when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);

        //test
        task.execute(execution);

        //verify
       // verify(execution).setVariable(ProcessEnv.ISSUE,jiraIssue);

    }
}
