//package main;
//
//
//import com.example.issueRemindEmailSender.ProcessEnv;
//import com.example.issueRemindEmailSender.model.JiraIssue;
//import com.example.issueRemindEmailSender.service.JiraServiceAPI;
//import com.example.issueRemindEmailSender.task.GetAllIssuesTask;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
//import org.joda.time.DateTime;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetAllIssuesTaskTest {
//
//    @Mock
//    private DelegateExecution execution;
//
//    @InjectMocks
//    private GetAllIssuesTask task;
//
//    @Mock
//    private JiraServiceAPI jiraServiceAPI;
//    @Mock
//    private MessageCorrelationBuilder correlationBuilder;
//
//    @Test
//    public void testExecute() {
//        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-29T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
//        JiraIssue jiraIssue2 = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-30T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("In progress").build();
//        List<JiraIssue> jiraIssueList = new ArrayList<>();
//        jiraIssueList.add(jiraIssue2);
//        jiraIssueList.add(jiraIssue);
//        when(jiraServiceAPI.getIssuesFields()).thenReturn(jiraIssueList);
//        when(jiraServiceAPI.areNeedIssuePresents(jiraIssueList)).thenReturn(jiraIssueList.isEmpty());
////        when(correlationBuilder.setVariable(ProcessEnv.ARE_NEED_ISSUES_PRESENT, true)).thenReturn(correlationBuilder);
////        when(execution.getVariable(ProcessEnv.ISSUE)).thenReturn(jiraIssue);
////        when(correlationBuilder.setVariable(ProcessEnv.JIRA_ISSUES, jiraIssueList)).thenReturn(correlationBuilder);
//
//
//        //test
//        task.execute(execution);
//
//        //verify
//       // verify(execution).getVariable(ProcessEnv.JIRA_ISSUES);
//        verify(execution).setVariable(ProcessEnv.JIRA_ISSUES,jiraIssueList);
//        verify(execution).setVariable(ProcessEnv.ARE_NEED_ISSUES_PRESENT,jiraIssueList.isEmpty());
//    }
//
//    @Test
//    public void testExecute2() {
//
//    }
//
//}
