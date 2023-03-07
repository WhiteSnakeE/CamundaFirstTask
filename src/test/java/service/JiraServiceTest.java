package service;

import com.example.issueRemindEmailSender.model.JiraIssue;
import com.example.issueRemindEmailSender.service.JiraService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import service.util.JiraRepositoryMock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JiraServiceTest {
//    private static final Long ID = 14403L;
//    private static final String CREATE_DATE = "2023-01-19T14:04:38.080+0200";
//    private static final String UPDATE_DATE = "2023-01-19T14:04:14.032+0200";
//    private static final String STATUS = "In Progress";
//
//    JiraService jiraService = new JiraService(new JiraRepositoryMock());
//
//    public JiraServiceTest() {
//
//    }
//
//    @Test
//    void happy() throws Exception {
//        Iterable<JiraIssue> issues = jiraService.getIssuesFields();
//
//        List<JiraIssue> jiraIssueList = new ArrayList<>((Collection<? extends JiraIssue>) issues);
//        JiraIssue jiraIssue =
//                new JiraIssue(14411L,
//                        DateTime.parse("2023-01-30T10:53:22.289+0200"),
//                        DateTime.parse("2023-01-30T10:53:18.127+0200"),
//                        "vladyslav.kharchenko@sytoss.com",
//                        "In Progress",
//                        0);
//
//        assertEquals(jiraIssueList.size(), 5);
//        assertEquals(jiraIssueList.get(0), jiraIssue);
//        assertEquals(jiraIssueList.get(1).getId(), ID);
//        assertEquals(jiraIssueList.get(2).getCreateDate(), DateTime.parse(CREATE_DATE));
//        assertEquals(jiraIssueList.get(3).getUpdateDate(), DateTime.parse(UPDATE_DATE));
//        assertEquals(jiraIssueList.get(4).getStatusName(), STATUS);
//
//    }


}


