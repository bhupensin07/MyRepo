package com.copilot.soprasteria.departmentservice.repository;

import com.copilot.soprasteria.departmentservice.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}
