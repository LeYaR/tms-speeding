package com.tms.speeding.repository;

import com.tms.speeding.entity.Region;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegionRepository extends PagingAndSortingRepository<Region, Integer> {
    
}
