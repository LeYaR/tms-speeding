package com.tms.speeding.repos;

import com.tms.speeding.entities.Country;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    
}
