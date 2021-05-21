package com.tms.speeding.repository;

import com.tms.speeding.entity.Department;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer> {
    
}
