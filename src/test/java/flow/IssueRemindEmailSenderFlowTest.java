package flow;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.service.SendEmailService;
import com.example.issueRemindEmailSender.task.*;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Deployment(resources = {
        "bpmn/notUpdatedIssueEmailSender.bpmn",
        "bpmn/applicationRules.dmn"
})
public class IssueRemindEmailSenderFlowTest {

    private final ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

    @Rule
    public final ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create(engine).build();

    @Mock
    JiraService jiraService;
    @Mock
    SendEmailService sendEmailService;

    @Mock
    private DelegateExecution execution;

    @Before
    public void setup() {
        Mocks.register("CalculateNotUpdateDays", new CalculateNotUpdateDaysTask());
        Mocks.register("GetAllIssues", new GetAllIssuesTask(jiraService));
        Mocks.register("GetStartTimeOfProcess", new GetStartTimeOfProcessTask());
        Mocks.register("SendEmailToEmployee", new SendEmailToEmployeeTask(sendEmailService));
        Mocks.register("SendEmailToTeamLead", new SendEmailToTeamLeadTask(sendEmailService));
    }

    @Test
    public void emailSendToEmployeeAndTeamLead() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-29T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
        JiraIssue jiraIssue2 = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-30T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("In progress").build();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue2);
        jiraIssueList.add(jiraIssue);
        when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main, times(2)).hasFinished("Event_164grpo");
        //verify


    }

    @Test
    public void needIssuesAreNotPresent() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        when(jiraService.areNeedIssuePresents(any())).thenReturn(false);


        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();


        //verify
        verify(main).hasFinished("Event_0j8msu4");


    }

    @Test
    public void issueUpdatedLessThanFiveDays() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-02-08T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("To Do").build();
        JiraIssue jiraIssue2 = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-02-09T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("test@gmail.com").statusName("In progress").build();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue2);
        jiraIssueList.add(jiraIssue);
        when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main, times(2)).hasFinished("Event_1bedetw");
        //verify

    }

    @Test
    public void emailNotSended() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-29T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("123").statusName("To Do").build();
        JiraIssue jiraIssue2 = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-01-30T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("123").statusName("In progress").build();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue2);
        jiraIssueList.add(jiraIssue);
        when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);


        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();


        //verify

    }

    @Test
    public void emailSendedOnlyToEmployee() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-02-03T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("123").statusName("To Do").build();
        JiraIssue jiraIssue2 = JiraIssue.builder().id(14411L).updateDate(DateTime.parse("2023-02-02T10:53:22.289+0200")).createDate(DateTime.parse("2023-01-30T10:53:18.127+0200")).email("123").statusName("In progress").build();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue2);
        jiraIssueList.add(jiraIssue);
        when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);


        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();


        //verify

    }
}
