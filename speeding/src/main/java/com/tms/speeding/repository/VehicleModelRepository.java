package com.tms.speeding.repository;

import com.tms.speeding.entity.VehicleModel;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface VehicleModelRepository extends PagingAndSortingRepository<VehicleModel, Integer> {
    
}
