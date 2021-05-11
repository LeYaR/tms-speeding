package com.tms.speeding.repos;

import com.tms.speeding.entities.Vehicle;

import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    
}
