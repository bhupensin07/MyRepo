package com.copilot.soprasteria.departmentservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;


public class DepartmentConfiguration {

    @Value("${employeeService.base.url}")
    private static String employeeBaseUrl;

    public static WebClient webClient(){
        return WebClient.builder().baseUrl(employeeBaseUrl).build();
    }

}
