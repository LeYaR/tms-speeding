package com.tms.speeding.repository;

import com.tms.speeding.entity.Rank;

import org.springframework.data.repository.CrudRepository;

public interface RankRepository extends CrudRepository<Rank, Integer> {
    
}
