package service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JiraServiceTest {
    private static final Long ID = 14100L;

    JiraService jiraService = new JiraService(new JiraRepositoryMock());

    public JiraServiceTest(){

    }

    @Test
    void getIssuesFields() throws Exception {
        Iterable<JiraIssue> issues = jiraService.getIssuesFields();
        JiraIssue jiraIssue = issues.iterator().next();
        assertEquals(jiraIssue.getId(),ID);

    }

}
