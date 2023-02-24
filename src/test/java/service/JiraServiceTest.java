package service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JiraServiceTest {
    private static final Long ID = 14388L;
   // private static final String email = 14388L;

    JiraService jiraService = new JiraService(new JiraRepositoryMock());

    public JiraServiceTest(){

    }

    @Test
    void getIssuesFields() throws Exception {
        Iterable<JiraIssue> issues = jiraService.getIssuesFields();
        List<JiraIssue> jiraIssueList = new ArrayList<>((Collection<? extends JiraIssue>) issues);
        JiraIssue jiraIssue =
                new JiraIssue(14411L,
                        DateTime.parse("2023-01-30T10:53:22.289+0200"),
                        DateTime.parse("2023-01-30T10:53:18.127+0200"),
                        "vladyslav.kharchenko@sytoss.com",
                        "In Progress",
                        0);

        assertEquals(jiraIssueList.size(),5);
        assertEquals(jiraIssueList.get(0),jiraIssue);
        //JiraIssue jiraIssue = issues.iterator().next();
//        assertEquals(jiraIssueList.get(4).getId(),ID);
//        assertEquals(jiraIssueList.get(0).getEmail(),ID);
//        assertEquals(jiraIssueList.get(1).getEmail(),ID);
//        assertEquals(jiraIssueList.get(2).getEmail(),ID);
//        assertEquals(jiraIssueList.get(3).getEmail(),ID);
//        assertEquals(jiraIssueList.get(4).getEmail(),ID);


    }

}
