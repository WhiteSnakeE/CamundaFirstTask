package com.example.firstCamundaTask.Jira;
import com.example.firstCamundaTask.model.JiraIssue;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("vlad")
@PropertySource("classpath:application-vlad.yaml")
public class JiraRequest {

    @Value(value = "${jira.user}") private String jiraUser;
    @Value(value = "${jira.password}") private String jiraPassword;

    private HttpResponse<JsonNode> sendRequest() throws UnirestException {
        String request = "https://sytoss.atlassian.net/rest/api/3/search?jql=project%20=%20CamundaTraning%20and%20status%20!=%20done%20and%20updated%3E-10d&fields=created,updated,status,creator";
        return Unirest.get(request)
                .header("Accept", "application/json")
                .basicAuth("vladyslav.kharchenko@sytoss.com", "")
                .asJson();
    }

    public List<JiraIssue> getIssuesFields() throws UnirestException {
        HttpResponse<JsonNode> response = sendRequest();
        int countOfIssues = (int) response.getBody().getObject().get("total");
        List<JiraIssue> issueList  = new ArrayList<>(countOfIssues);
        for (int i = 0; i < countOfIssues; i++) {
            String id =  response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).get("id").toString();
            String createdDate = response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").get("created").toString();
            String updatedDate = response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").get("updated").toString();
            String email = response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").getJSONObject("creator").get("emailAddress").toString();
            String status = response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").getJSONObject("status").get("name").toString();

            issueList.add(JiraIssue.builder().id(Integer.valueOf(id)).createDate(DateTime.parse(createdDate))
                    .updateDate(DateTime.parse(updatedDate)).email(email).statusName(status).build());
        }
        return issueList;
    }

    public boolean areNeedIssuePresents() throws UnirestException {
        HttpResponse<JsonNode> response = sendRequest();
        int countOfIssues = (int)response.getBody().getObject().get("total");
        return countOfIssues > 0;
    }

    public int lastUpdateDays(JiraIssue issue){
        if(DateTime.now().getDayOfMonth()==issue.getUpdateDate().getDayOfMonth()) return 0;
        return (DateTime.now()
                .minusSeconds(issue.getUpdateDate().getSecondOfMinute())
                .minusMinutes(issue.getUpdateDate().getMinuteOfHour())
                .minusHours(issue.getUpdateDate().getHourOfDay())
                .minusDays(issue.getUpdateDate().getDayOfMonth()).getDayOfMonth());
    }

}
