package com.tms.speeding.repository;

import com.tms.speeding.dbo.RankDbo;

import org.springframework.data.repository.CrudRepository;

public interface RankRepository extends CrudRepository<RankDbo, Integer> {
    
}
