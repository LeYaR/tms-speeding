package com.tms.speeding.repository;

import com.tms.speeding.entity.Country;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {
    
}
