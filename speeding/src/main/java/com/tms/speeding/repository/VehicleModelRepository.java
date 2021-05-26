package com.tms.speeding.repository;



import com.tms.speeding.domain.dbo.VehicleModelDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VehicleModelRepository extends PagingAndSortingRepository<VehicleModelDbo, Integer> {
    
}
