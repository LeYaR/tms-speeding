package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.RegionDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegionRepository extends PagingAndSortingRepository<RegionDbo, Integer> {
    RegionDbo findByTitle(String title);
}
