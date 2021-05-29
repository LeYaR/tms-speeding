package com.tms.speeding.repository;


import com.tms.speeding.domain.dbo.VehicleModelDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VehicleModelRepository extends PagingAndSortingRepository<VehicleModelDbo, Integer> {
    Optional<VehicleModelDbo> findByTitle(String title);

}
