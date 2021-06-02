package com.tms.speeding.repository;

import java.util.Optional;

import com.tms.speeding.domain.dbo.VehicleDbo;
import com.tms.speeding.domain.dto.VehicleDto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends PagingAndSortingRepository<VehicleDbo, Integer> {
     String QUERY_BASE = " from VehicleDbo v left join v.model m"
                            + " left join m.mark k left join v.region r "
                            + " where lower(v.vin) like %?1%"
                            + " or lower(v.regNumber) like %?1%"
                            + " or lower(m.title) like %?1%"
                            + " or lower(k.title) like %?1%"
                            + " or lower(r.title) like %?1%";
    
     String QUERY_CHECK = "select * from sv_vehicles where"
                        + " lower(vin) = lower(?#{#vehicle.vin})"
                        + " or lower(reg_number) = lower(?#{#vehicle.regNumber}) limit 1";

    @Query(value = "select v" + QUERY_BASE)
    Iterable<VehicleDbo> findByAll(String search);

    @Query(value = "select count(*)" + QUERY_BASE)
    long countBySearch(String search);

    @Query(value = "select v" + QUERY_BASE, countQuery = "select count(*)" + QUERY_BASE)
    Page<VehicleDbo> findByAll(String search, Pageable pageable);

    @Query(value = QUERY_CHECK, nativeQuery = true)
    Optional<VehicleDbo> findByAll(@Param("vehicle") VehicleDto vehicle);
}
