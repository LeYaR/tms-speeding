package com.tms.speeding.repository;

import com.tms.speeding.entity.VehicleMark;

import org.springframework.data.repository.CrudRepository;

public interface VehicleMarkRepository extends CrudRepository<VehicleMark, Integer> {
    
}
