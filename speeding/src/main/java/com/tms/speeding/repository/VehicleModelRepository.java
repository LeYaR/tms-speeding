package com.tms.speeding.repository;

import com.tms.speeding.entity.VehicleModel;

import org.springframework.data.repository.CrudRepository;

public interface VehicleModelRepository extends CrudRepository<VehicleModel, Integer> {
    
}
