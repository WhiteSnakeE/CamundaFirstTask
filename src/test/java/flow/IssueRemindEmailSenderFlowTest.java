package flow;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.service.SendEmailService;
import com.example.issueRemindEmailSender.task.*;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
    public void emailSendToEmployeeAndTeamLead() throws IOException {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = getJiraIssue();
        JiraIssue jiraIssue1 = getJiraIssue();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue1);
        jiraIssueList.add(jiraIssue);
        // when(execution.getVariable(ProcessEnv.ARE_NEED_ISSUES_PRESENT)).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutes");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, times(2)).hasCompleted("AllIssuesCollected");
        verify(main, times(2)).hasCompleted("SendEmailToEmployeeTask");
        verify(main, times(2)).hasCompleted("SendEmailToTeamLeadTask");
        verify(main, times(2)).hasFinished("EmailSendedToBossAndEmployee");


    }

    @Test
    public void needIssuesAreNotPresent() {
        ProcessScenario main = mock(ProcessScenario.class);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutes");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, never()).hasStarted("AllIssuesCollected");
        verify(main).hasFinished("ProcedureEnded");

    }

    @Test
    public void issueUpdatedLessThanFiveDays() throws IOException {
        ProcessScenario main = mock(ProcessScenario.class);
        DateTime dateTime = new DateTime();
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setUpdateDate(dateTime);
        JiraIssue jiraIssue1 = getJiraIssue();
        jiraIssue1.setUpdateDate(dateTime);
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue1);
        jiraIssueList.add(jiraIssue);
        //when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutes");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, times(2)).hasCompleted("AllIssuesCollected");
        verify(main, never()).hasCompleted("SendEmailToEmployeeTask");
        verify(main, times(2)).hasFinished("Nothing");


    }

    @Test
    public void emailNotSended() throws IOException {
        ProcessScenario main = mock(ProcessScenario.class);
        JiraIssue jiraIssue = getJiraIssue();
        JiraIssue jiraIssue1 = getJiraIssue();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue1);
        jiraIssueList.add(jiraIssue);
        // when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);
        when(sendEmailService.send(anyString(), anyString())).thenThrow(new BpmnError("SOLVIT_ERROR"));
        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutes");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, times(2)).hasCompleted("AllIssuesCollected");
        verify(main, times(2)).hasCanceled("SendEmailToEmployeeTask");
        verify(main, times(2)).hasFinished("MessageNotSend");
        verify(main).hasFinished("ProcedureEnded");


    }

    @Test
    public void emailSendedOnlyToEmployee() throws IOException {
        ProcessScenario main = mock(ProcessScenario.class);
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusDays(5);
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setUpdateDate(dateTime);
        JiraIssue jiraIssue1 = getJiraIssue();
        jiraIssue1.setUpdateDate(dateTime);
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue1);
        jiraIssueList.add(jiraIssue);
        // when(jiraService.areNeedIssuePresents(any())).thenReturn(true);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();


        verify(main).hasStarted("EveryFiveMinutes");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, times(2)).hasCompleted("AllIssuesCollected");
        verify(main, times(2)).hasCompleted("SendEmailToEmployeeTask");
        verify(main, times(2)).hasFinished("EmailSendedToEmployee");
        verify(main, never()).hasCompleted("SendEmailToTeamLeadTask");

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
