package com.tms.speeding.repository;



import com.tms.speeding.domain.dbo.DepartmentDbo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentDbo, Integer> {
    
}
