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
        System.out.println(jiraPassword);
        System.out.println(jiraUser);
        String request = "https://sytoss.atlassian.net/rest/api/3/search?jql=project%20=%20CamundaTraning%20and%20status%20!=%20done%20and%20updated%3E-10d&fields=updated,status";
        return Unirest.get(request)
                .header("Accept", "application/json")
                .basicAuth("vladyslav.kharchenko@sytoss.com", "f6NtkmUF3K3dGI5EWFPe1AB6")
                .asJson();
    }

    public List<JiraIssue> getIssuesFields() throws UnirestException {
        HttpResponse<JsonNode> response = sendRequest();
        int countOfIssues = (int)response.getBody().getObject().get("total");
        List<JiraIssue> issueList  = new ArrayList<>(countOfIssues);
        for (int i = 0; i < countOfIssues; i++) {
            String id = (String) response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).get("id");
            String dateTime = (String) response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").get("updated");
            String statusName = (String) response.getBody().getObject().getJSONArray("issues").
                    getJSONObject(i).getJSONObject("fields").getJSONObject("status").get("name");
            issueList.add(JiraIssue.builder().id(Integer.valueOf(id)).date(DateTime.parse(dateTime)).statusName(statusName).build());
        }
        return issueList;
    }

}
