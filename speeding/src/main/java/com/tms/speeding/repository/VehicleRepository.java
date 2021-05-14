package com.tms.speeding.repository;

import com.tms.speeding.entity.Vehicle;

import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    
}
