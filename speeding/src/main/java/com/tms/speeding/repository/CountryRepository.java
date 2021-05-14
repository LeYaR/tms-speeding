package com.tms.speeding.repository;

import com.tms.speeding.entity.Country;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    
}
