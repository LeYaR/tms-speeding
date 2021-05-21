package com.tms.speeding.repository;

import com.tms.speeding.entity.Rank;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RankRepository extends PagingAndSortingRepository<Rank, Integer> {
    
}
