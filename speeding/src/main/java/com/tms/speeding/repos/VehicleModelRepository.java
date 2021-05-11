package com.tms.speeding.repos;

import com.tms.speeding.entities.VehicleModel;

import org.springframework.data.repository.CrudRepository;

public interface VehicleModelRepository extends CrudRepository<VehicleModel, Integer> {
    
}
