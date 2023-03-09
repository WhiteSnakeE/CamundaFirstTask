package flow;

import com.example.issueRemindEmailSender.ProcessEnv;
import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.EmailMessage;
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
import org.camunda.bpm.extension.mockito.ProcessExpressions;
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
        "bpmn/Issue-Remind-Email-Sender-child-issue-Analyze.bpmn",
        "bpmn/Issue-Remind-Email-Sender-main.bpmn",
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
        Mocks.register("CheckProjectName", new CheckProjectNameTask(jiraService));
    }

    @Test
    public void emailSendToEmployeeAndTeamLead() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setUpdateDate(new DateTime().minusDays(5));
        JiraIssue jiraIssue1 = getJiraIssue();
        jiraIssue1.setUpdateDate(new DateTime().minusDays(10));
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue1);
        jiraIssueList.add(jiraIssue);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, times(2)).hasCompleted("IssueProcessing");
        verify(main).hasCompleted("IssueProcessing#multiInstanceBody");
    }

    @Test
    public void needIssuesAreNotPresent() {
        ProcessScenario main = mock(ProcessScenario.class);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main, never()).hasStarted("AllIssuesCollectedStartEvent");
        verify(main).hasFinished("IssueDoesntExist");

    }

    @Test
    public void issueUpdatedLessThanFiveDays() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setUpdateDate(new DateTime());
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main).hasCompleted("IssueProcessing");
        verify(main).hasCompleted("IssueProcessing#multiInstanceBody");
        verify(main).hasFinished("ProcedureEndEvent");


    }

    @Test
    public void emailNotSent() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        JiraIssue jiraIssue = getJiraIssue();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);
        when(sendEmailService.send(anyString(), anyString())).thenThrow(new BpmnError("SOLVIT_ERROR"));
        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("IssueProcessing");
        verify(main).hasCompleted("IssueProcessing#multiInstanceBody");



    }

    @Test
    public void emailSentOnlyToEmployee() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setUpdateDate( new DateTime().minusDays(5));
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("IssueProcessing");
        verify(main).hasCompleted("IssueProcessing#multiInstanceBody");
        verify(main).hasFinished("ProcedureEndEvent");
    }
    @Test
    public void emailNotSentToTeamLead() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        JiraIssue jiraIssue = getJiraIssue();
        List<JiraIssue> jiraIssueList = new ArrayList<>();
        jiraIssueList.add(jiraIssue);
        String message1 = EmailMessage.setMessageBoss(jiraIssue);
        String message = EmailMessage.setMessageEmployee(jiraIssue);
        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);
        when(sendEmailService.send(jiraIssue.getEmail(),message)).thenThrow(new BpmnError("SOLVIT_ERROR"));
        when(execution.getVariable(ProcessEnv.EMAIL)).thenReturn("vlad.kharchenko2003@gmail.com");
        when(sendEmailService.send(execution.getVariable(ProcessEnv.EMAIL).toString(),message1)).thenThrow(new BpmnError("SOLVIT_ERROR"));

        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");
        verify(main).hasCompleted("CheckProjectNameTask");
        verify(main).hasCompleted("IssueProcessing");
        verify(main).hasCompleted("IssueProcessing#multiInstanceBody");
        verify(main).hasFinished("ProcedureEndEvent");

    }
    @Test
    public void allSubProcessTest() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        ProcessScenario child = mock(ProcessScenario.class);
        when(main.runsCallActivity("IssueProcessing")).thenReturn(Scenario.use(child));
        List<JiraIssue> jiraIssueList = new ArrayList<>();

        JiraIssue jiraIssueWrong = getJiraIssue();
        jiraIssueWrong.setEmail("wrong email");
        jiraIssueList.add(jiraIssueWrong);

        JiraIssue jiraIssueToEmp = getJiraIssue();
        jiraIssueToEmp.setUpdateDate(new DateTime().minusDays(6));
        jiraIssueList.add(jiraIssueToEmp);

        JiraIssue jiraIssueToTeamLead = getJiraIssue();
        jiraIssueToTeamLead.setUpdateDate(new DateTime().minusDays(11));
        jiraIssueList.add(jiraIssueToTeamLead);

        JiraIssue jiraIssueNotSend = getJiraIssue();
        jiraIssueNotSend.setUpdateDate(new DateTime());
        jiraIssueList.add(jiraIssueNotSend);

        when(jiraService.getIssuesFields()).thenReturn(jiraIssueList);
        String message = EmailMessage.setMessageEmployee(jiraIssueWrong);
        String message1 = EmailMessage.setMessageBoss(jiraIssueWrong);
        when(sendEmailService.send(jiraIssueWrong.getEmail(),message)).thenThrow(new BpmnError("SOLVIT_ERROR"));
        when(execution.getVariable(ProcessEnv.EMAIL)).thenReturn("surtx0119@gmail.com");
        when(sendEmailService.send(execution.getVariable(ProcessEnv.EMAIL).toString(),message1)).thenThrow(new BpmnError("SOLVIT_ERROR"));


        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main).hasCompleted("GetAllIssuesTask");

    }

    @Test
    public void jiraWasNotTaken() throws Exception {
        ProcessScenario main = mock(ProcessScenario.class);
        when(jiraService.getIssuesFields()).thenThrow(new BpmnError("SOLVIT_ERROR"));
        Scenario.run(main).
                startByKey("IssueRemindEmailSender"
                ).execute();

        verify(main).hasStarted("EveryFiveMinutesStartEvent");
        verify(main).hasCompleted("GetStartTimeOfProcessTask");
        verify(main,times(3)).hasCanceled("GetAllIssuesTask");
        verify(main,times(3)).hasFinished("IsItThirdAttemptGateway");
        verify(main,times(2)).hasFinished("PauseOneMinuteTimerCatchEvent");
        verify(main).hasFinished("JiraWasNotResponceEndEvent");


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
