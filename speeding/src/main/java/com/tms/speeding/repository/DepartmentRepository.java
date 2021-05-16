package com.tms.speeding.repository;

import com.tms.speeding.dbo.DepartmentDbo;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<DepartmentDbo, Integer> {
    
}
