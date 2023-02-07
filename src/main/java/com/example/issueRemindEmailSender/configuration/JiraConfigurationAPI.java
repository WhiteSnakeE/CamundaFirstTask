package com.example.issueRemindEmailSender.configuration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
//
//@Configuration
//@Profile({"dev"})
//public class JiraConfigurationAPI {


//    @Bean
//    public HttpResponse<JsonNode> sendRequest( String url ,String jiraUser, String jiraPassword) throws UnirestException {
//        String request = url;
//        return Unirest.get(request)
//                .header("Accept", "application/json")
//                .basicAuth(jiraUser, jiraPassword)
//                .asJson();
//    }
//}
