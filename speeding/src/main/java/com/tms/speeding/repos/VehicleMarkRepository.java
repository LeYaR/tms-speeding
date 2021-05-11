package com.tms.speeding.repos;

import com.tms.speeding.entities.VehicleMark;

import org.springframework.data.repository.CrudRepository;

public interface VehicleMarkRepository extends CrudRepository<VehicleMark, Integer> {
    
}
