package service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.jira.rest.client.api.domain.User;
import com.example.issueRemindEmailSender.repository.JiraRepository;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile({"dev"})
public class JiraRepositoryMock implements JiraRepository {

    public SearchResult getIssuesFields(String jql, int maxResults) {
        Issue jiraIssue = null;
        Issue jiraIssue1 = null;

        try {
            jiraIssue = getJiraIssue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
             jiraIssue1 = getJiraIssue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Issue> listIssues = new ArrayList<>();
        if (maxResults > 0) {
            listIssues.add(jiraIssue);
            listIssues.add(jiraIssue1);
        }
        User user = new User(null,null,null,"vladyslav.kharchenko@sytoss.com",null,null,null);
        Issue firstIssue = new Issue(null, null, null, 14100L, null, null, null, null, null, null, null, user, null, DateTime.parse("2023-01-30T10:53:22.289+0200"), DateTime.parse("2023-01-30T10:53:22.289+0200"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        System.out.println(firstIssue);
        return new SearchResult(0, maxResults, 0, listIssues);
    }

    private Issue getJiraIssue() throws IOException {
        String object = IOUtils.resourceToString("/data/todo.json", Charset.defaultCharset());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(object, Issue.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Can not deserialize solvitCase", e);
        }
    }
}