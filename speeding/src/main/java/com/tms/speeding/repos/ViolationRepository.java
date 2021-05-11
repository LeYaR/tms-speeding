package com.tms.speeding.repos;

import com.tms.speeding.entities.Violation;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ViolationRepository extends PagingAndSortingRepository<Violation, Number> {
    
}
