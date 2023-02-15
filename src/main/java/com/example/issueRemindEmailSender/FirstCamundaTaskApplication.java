package com.example.issueRemindEmailSender;




import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackages = {"com.example"})
@Profile("!springtest")
public class FirstCamundaTaskApplication  {


    public static void main(String[] args) {
        SpringApplication.run(FirstCamundaTaskApplication.class, args);
    }

}
