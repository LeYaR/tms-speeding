package com.tms.speeding.repos;

import com.tms.speeding.entities.Rank;

import org.springframework.data.repository.CrudRepository;

public interface RankRepository extends CrudRepository<Rank, Integer> {
    
}
