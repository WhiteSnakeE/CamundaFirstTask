//package com.example.firstCamundaTask.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.camunda.bpm.BpmPlatform;
//import org.camunda.bpm.ProcessEngineService;
//import org.camunda.bpm.engine.ProcessEngine;
//import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
//import org.camunda.bpm.engine.spring.application.SpringServletProcessApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//@ComponentScan("com.example")
//@Profile("!springtest")
//@Slf4j
//public class CamundaConfiguration {
//
//	@Bean
//	public ProcessEngineService processEngineService() {
//		return BpmPlatform.getProcessEngineService();
//	}
//
//	@Bean(destroyMethod = "")
//	public ProcessEngine processEngine() {
//		return processEngineService().getDefaultProcessEngine();
//	}
//
//	@Bean(name = "CamundaFirstTask")
//	public SpringServletProcessApplication processApplication() {
//		return new SpringServletProcessApplication();
//	}
//
//	@Bean
//	public StandaloneProcessEngineConfiguration springProcessEngineConfiguration() {
//		return new StandaloneProcessEngineConfiguration();
//	}
//
//}
