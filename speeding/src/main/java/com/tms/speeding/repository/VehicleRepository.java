package com.tms.speeding.repository;

import com.tms.speeding.dbo.VehicleDbo;

import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<VehicleDbo, Integer> {
    
}
