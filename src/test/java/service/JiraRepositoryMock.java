package service;

import com.atlassian.jira.rest.client.api.StatusCategory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.jira.rest.client.api.domain.User;
import com.example.issueRemindEmailSender.repository.JiraRepository;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile({"dev"})
public class JiraRepositoryMock implements JiraRepository {

    public SearchResult getIssuesFields(String jql, int maxResults) {

        try {
            return getSearchResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SearchResult getSearchResult() throws IOException {

        String object = IOUtils.resourceToString("/data.json", Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Issue> issueList = new ArrayList<>();
        int startAt = 0;
        int maxResults = 0;
        int total = 0;
        try {
            JsonNode jsonIssues = objectMapper.readTree(object);
            String issues = jsonIssues.get("issues").toString();
            startAt = jsonIssues.get("startAt").asInt();
            maxResults = jsonIssues.get("maxResults").asInt();
            total = jsonIssues.get("total").asInt();
            List<Object> listIssues
                    = objectMapper.readValue(issues, new TypeReference<List<Object>>() {
            });

            for (Object issue : listIssues) {
                JsonNode node = objectMapper.convertValue(issue, JsonNode.class);
                Map<String, URI> avatarUris = new HashMap<>();
                String updated = node.get("fields").get("updated").asText();
                String created = node.get("fields").get("created").asText();
                avatarUris.put("48x48",URI.create(node.get("fields").get("creator").get("avatarUrls").get("48x48").asText()));
                avatarUris.put("24x24",URI.create(node.get("fields").get("creator").get("avatarUrls").get("24x24").asText()));
                avatarUris.put("16x16",URI.create(node.get("fields").get("creator").get("avatarUrls").get("16x16").asText()));
                avatarUris.put("32x32",URI.create(node.get("fields").get("creator").get("avatarUrls").get("32x32").asText()));
                User user = new User(null,null,null,null,node.get("fields").get("creator").get("emailAddress").asText(),true,null,avatarUris,null);
                Status status = new Status(null,null,node.get("fields").get("status").get("name").asText(),null,null,null);
                Issue needIssue = new Issue(null, null, null, node.get("id").asLong(), null, null, status, null, null, null, null, user, null, DateTime.parse(created), DateTime.parse(updated), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                issueList.add(needIssue);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        return new SearchResult(startAt, maxResults, total, issueList);
    }
}

