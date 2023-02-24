package com.example.issueRemindEmailSender;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackages = {"com.example"})
@Profile("!springtest")
public class FirstCamundaTaskApplication {


    public static void main(String[] args) {
        SpringApplication.run(FirstCamundaTaskApplication.class, args);
    }

}
