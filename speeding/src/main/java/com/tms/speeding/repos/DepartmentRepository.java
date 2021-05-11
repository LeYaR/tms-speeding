package com.tms.speeding.repos;

import com.tms.speeding.entities.Department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    
}
