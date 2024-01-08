package com.copilot.soprasteria.departmentservice.service;

import com.copilot.soprasteria.departmentservice.model.Department;
import com.copilot.soprasteria.departmentservice.model.Employee;
import com.copilot.soprasteria.departmentservice.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repo;

    @Value("${employeeService.base.url}")
    private String employeeServiceBaseUri;

    private RestTemplate template;
    public DepartmentService(@Value("${employeeService.base.url}") String employeeServiceBaseUri, RestTemplateBuilder builder){
        this.template = builder.rootUri(employeeServiceBaseUri).build();
    }

    public Department saveDepartment(Department dep){
        return repo.save(dep);
    }

    public List<Department> getAllDepartments(){
        return (List<Department>) repo.findAll();
    }

    public String getEmployeeByDepId(int depId){
        return Arrays.toString(template.getForObject( "/employeeByDep/" + depId, Employee[].class));
    }
    public ResponseEntity<Employee[]> getEmployeeByDepId2(int depId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-COM-PERSIST", "NO");
        headers.set("X-COM-LOCATION", "USA");
        HttpEntity<Employee> entity = new HttpEntity<>(headers);
        ResponseEntity<Employee[]> responseEntity = template.exchange("/employeeByDep/"+depId, HttpMethod.GET, entity, Employee[].class);
        return responseEntity;
    }
}
