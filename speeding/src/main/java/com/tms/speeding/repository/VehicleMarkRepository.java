package com.tms.speeding.repository;

import com.tms.speeding.entity.VehicleMark;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface VehicleMarkRepository extends PagingAndSortingRepository<VehicleMark, Integer> {
    
}
