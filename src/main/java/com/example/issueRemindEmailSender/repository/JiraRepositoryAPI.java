//package com.example.issueRemindEmailSender.repository;
//
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class JiraRepositoryAPI implements JiraRepositoryRequest{
//
//    @Value(value = "${jira.user}") private String jiraUser;
//    @Value(value = "${jira.password}") private String jiraPassword;
//    @Value(value = "${jira.url}")  private  String url;
//
//    public HttpResponse<JsonNode> sendRequest() throws UnirestException {
//        String request = url;
//        return Unirest.get(request)
//                .header("Accept", "application/json")
//                .basicAuth(jiraUser, jiraPassword)
//                .asJson();
//    }
//
//    @Override
//    public HttpResponse<JsonNode> getIssueFields() {
//        HttpResponse<JsonNode> httpResponse = null;
//        try {
//            httpResponse = sendRequest();
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        return httpResponse;
//    }
//
//
//}
