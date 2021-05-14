package com.tms.speeding.repository;

import com.tms.speeding.entity.Violation;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ViolationRepository extends PagingAndSortingRepository<Violation, Number> {
    
}
