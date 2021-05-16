package com.tms.speeding.repository;

import com.tms.speeding.dbo.VehicleMarkDbo;

import org.springframework.data.repository.CrudRepository;

public interface VehicleMarkRepository extends CrudRepository<VehicleMarkDbo, Integer> {
    
}
