package com.tms.speeding.repository;


import com.tms.speeding.domain.dbo.RankDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RankRepository extends PagingAndSortingRepository<RankDbo, Integer> {

    Optional<RankDbo> findByTitle(String title);

}
