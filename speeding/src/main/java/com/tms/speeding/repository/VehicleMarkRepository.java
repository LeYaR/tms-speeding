package com.tms.speeding.repository;



import com.tms.speeding.domain.dbo.VehicleMarkDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VehicleMarkRepository extends PagingAndSortingRepository<VehicleMarkDbo, Integer> {
    
}
