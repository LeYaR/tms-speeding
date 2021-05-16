package com.tms.speeding.repository;

import com.tms.speeding.dbo.VehicleModelDbo;

import org.springframework.data.repository.CrudRepository;

public interface VehicleModelRepository extends CrudRepository<VehicleModelDbo, Integer> {
    
}
