package com.example.issueRemindEmailSender.repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.springframework.stereotype.Repository;

@Repository
public class JiraRepositoryAPI implements JiraRepositoryRequest{

    private final HttpResponse<JsonNode> httpResponse;

    public JiraRepositoryAPI(HttpResponse<JsonNode> httpResponse) {
        this.httpResponse = httpResponse;
    }
    @Override
    public HttpResponse<JsonNode> getIssueFields() {
       return httpResponse;
    }

    @Override
    public String getId(int JSONObject) {
        return httpResponse.getBody().getObject().getJSONArray("issues").
                getJSONObject(JSONObject).get("id").toString();
    }

    @Override
    public String getCreateDate(int JSONObject) {
        return  httpResponse.getBody().getObject().getJSONArray("issues").
                getJSONObject(JSONObject).getJSONObject("fields").get("created").toString();
    }

    @Override
    public String getUpdateDate(int JSONObject) {
        return httpResponse.getBody().getObject().getJSONArray("issues").
                getJSONObject(JSONObject).getJSONObject("fields").get("updated").toString();
    }

    @Override
    public String getEmail(int JSONObject) {
        return httpResponse.getBody().getObject().getJSONArray("issues").
                getJSONObject(JSONObject).getJSONObject("fields").getJSONObject("creator").get("emailAddress").toString();
    }

    @Override
    public String getStatus(int JSONObject) {
        return httpResponse.getBody().getObject().getJSONArray("issues").
                getJSONObject(JSONObject).getJSONObject("fields").getJSONObject("status").get("name").toString();
    }

    @Override
    public int getTotalIssues() {
        return (int) httpResponse.getBody().getObject().get("total");
    }


}
