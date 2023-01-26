package com.example.firstCamundaTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.example"})
@Profile("!springtest")
public class FirstCamundaTaskApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(FirstCamundaTaskApplication.class).sources(CamundaConfiguration.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(FirstCamundaTaskApplication.class, args);
    }
}
