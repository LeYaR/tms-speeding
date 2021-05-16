package com.tms.speeding.repository;

import com.tms.speeding.dbo.CountryDbo;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryDbo, Integer> {
    
}
