package com.tms.speeding.repository;

import com.tms.speeding.dbo.RegionDbo;

import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<RegionDbo, Integer> {
    
}
