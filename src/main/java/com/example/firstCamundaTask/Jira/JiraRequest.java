package com.example.firstCamundaTask.Jira;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JiraRequest {


    public String sendRequest() throws UnirestException {
        String request = "https://sytoss.atlassian.net/rest/api/3/search?jql=project%3DCamundaTraning%20and%20status!%3Ddone%20and%20updated%3E-10d&fields=updated";
        HttpResponse<JsonNode> response =
                Unirest.get(request)
                        .header("Accept", "application/json")
                        .basicAuth("vladyslav.kharchenko@sytoss.com", "AaITjz8BVpgLAMowN407B40B")
                        .asJson();
        return response.getBody().toString();
    }

}
