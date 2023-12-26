package com.copilot.soprasteria.departmentservice.service;

import com.copilot.soprasteria.departmentservice.model.Department;
import com.copilot.soprasteria.departmentservice.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repo;

    public Department saveDepartment(Department dep){
        return repo.save(dep);
    }

    public List<Department> getAllDepartments(){
        return (List<Department>) repo.findAll();
    }
}
