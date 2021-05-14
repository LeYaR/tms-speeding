package com.tms.speeding.repository;

import com.tms.speeding.entity.Region;

import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region, Integer> {
    
}
