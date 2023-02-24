package service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.EmailMessage;
import com.example.issueRemindEmailSender.service.JiraService;
import com.example.issueRemindEmailSender.service.SendEmailService;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)


public class SendEmailServiceTest {



    SendEmailService sendEmailService = new SendEmailService(new SendEmailRepositoryMock());

    public SendEmailServiceTest(){

    }
    @Test
    void sendEmail() throws Exception {
        JiraIssue jiraIssue = getJiraIssue();
        assertEquals(jiraIssue.getEmail(),"surtx0119@gmail.com");
        //when(sendEmailService.send(jiraIssue.getEmail(), EmailMessage.setMessageEmployee(jiraIssue))).thenReturn(true);
        assertTrue(sendEmailService.send(jiraIssue.getEmail(), EmailMessage.setMessageEmployee(jiraIssue)));

       // verify(sendEmailService);




    }
    private JiraIssue getJiraIssue() throws IOException {
        String object = IOUtils.resourceToString("/data/secondJiraIssue.json", Charset.defaultCharset());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(object, JiraIssue.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Can not deserialize solvitCase", e);
        }
    }
}
