package com.tms.speeding.repository;

import java.util.Optional;

import com.tms.speeding.dto.VehicleD;
import com.tms.speeding.entity.Vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Integer> {
    final String QUERY_BASE = " from sv_vehicles a left join sv_vehicle_models b on a.model_id = b.id"
                            + " left join sv_vehicle_marks c on b.mark_id = c.id left join sv_regions d on a.region_id = d.id"
                            + " where lower(a.vin) like %?1%"
                            + " or lower(a.reg_number) like %?1%"
                            + " or lower(b.title) like %?1%"
                            + " or lower(c.title) like %?1%"
                            + " or lower(d.title) like %?1%";
    
    final String QUERY_CHECK = "select * from sv_vehicles where"
                        + " lower(vin) = lower(?#{#vehicle.vin})"
                        + " or lower(reg_number) = lower(?#{#vehicle.regNumber}) limit 1";

    @Query(value = "select a.*" + QUERY_BASE, nativeQuery = true)
    Iterable<Vehicle> findByAll(String search);

    @Query(value = "select count(1)" + QUERY_BASE, nativeQuery = true)
    long countBySearch(String search);

    @Query(value = "select a.*" + QUERY_BASE, countQuery = "select count(1)" + QUERY_BASE, nativeQuery = true)
    Page<Vehicle> findByAll(String search, Pageable pageable);

    @Query(value = QUERY_CHECK, nativeQuery = true)
    Optional<Vehicle> findByAll(@Param("vehicle") VehicleD vehicle);
}
