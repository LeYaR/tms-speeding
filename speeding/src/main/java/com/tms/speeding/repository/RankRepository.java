package com.tms.speeding.repository;



import com.tms.speeding.domain.dbo.RankDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RankRepository extends PagingAndSortingRepository<RankDbo, Integer> {
    
}
