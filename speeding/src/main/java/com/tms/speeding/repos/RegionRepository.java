package com.tms.speeding.repos;

import com.tms.speeding.entities.Region;

import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region, Integer> {
    
}
