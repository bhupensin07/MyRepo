package com.copilot.soprasteria.departmentservice.controller;


import com.copilot.soprasteria.departmentservice.configuration.DepartmentConfiguration;
import com.copilot.soprasteria.departmentservice.model.Department;
import com.copilot.soprasteria.departmentservice.model.Employee;
import com.copilot.soprasteria.departmentservice.service.DepartmentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dep")
public class DepartmentController {
    //private static String EMPLOYEE_SERVICE_URL="http://localhost:8080/api";
    @Autowired
    private DepartmentService service;

   private RestTemplate template = new RestTemplate();

   private WebClient webClient = DepartmentConfiguration.webClient();

    @PostMapping("/addDep")
    public Department addDepartment(@RequestBody Department dep){
        return service.saveDepartment(dep);
    }

    @GetMapping(value = "/{depId}")
    @CircuitBreaker(name="randomDepartment", fallbackMethod = "randomError" )
    public ResponseEntity<Employee[]> getEmployeeByDepartment(@PathVariable int depId){
        return service.getEmployeeByDepId2(depId);
    }
    @GetMapping(value = "/t2/{depId}")
    @HystrixCommand( fallbackMethod = "randomError2")
    public String getEmployeeByDepartment2(@PathVariable int depId){
       return  service.getEmployeeByDepId(depId);
    }

    @GetMapping("/getAllDepartments")
    public List<Department> getAllDepartments(){
        return service.getAllDepartments();
    }

    public ResponseEntity<Object> randomError(int x, Throwable error) {
    	return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("Employee Service is down please wait....");
    }
    public String randomError2(int x, Throwable error) {
        return "Employee Service is down please wait....";
    }
}
