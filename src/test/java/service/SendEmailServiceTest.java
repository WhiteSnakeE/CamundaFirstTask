package service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.EmailMessage;
import com.example.issueRemindEmailSender.service.SendEmailService;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import service.util.SendEmailRepositoryMock;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)


public class SendEmailServiceTest {

    SendEmailService sendEmailService = new SendEmailService(new SendEmailRepositoryMock());

    public SendEmailServiceTest() {

    }

    @Test
    void happySendEmail() throws Exception {
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setEmail("surtx0119@gmail.com");
        assertEquals(jiraIssue.getEmail(), "surtx0119@gmail.com");
        assertTrue(sendEmailService.send(jiraIssue.getEmail(), EmailMessage.setMessageEmployee(jiraIssue)));


    }
    @Test
    void sendEmail() throws Exception {
        JiraIssue jiraIssue = getJiraIssue();
        jiraIssue.setEmail("surtxgmail.com");
        Assertions.assertThrows(BpmnError.class, () -> {
            assertTrue(sendEmailService.send(jiraIssue.getEmail(), EmailMessage.setMessageEmployee(jiraIssue)));
        });



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
