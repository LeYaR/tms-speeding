package com.tms.speeding.repository;

import com.tms.speeding.entity.Department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    
}
