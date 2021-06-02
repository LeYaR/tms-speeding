package com.tms.speeding.repository;

import java.util.Date;

import com.tms.speeding.domain.dbo.ViolationDbo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ViolationRepository extends PagingAndSortingRepository<ViolationDbo, Number>, JpaSpecificationExecutor<ViolationDbo> {
     String QUERY_BASE = " from sv_violations a"
                              + " left join sv_regions f on a.region_id = f.id, sv_people b, sv_inspectors c"
                              + " left join sv_ranks d on c.rank_id = d.id"
                              + " left join sv_departments e on c.department_id = e.id, sv_vehicles g, sv_people h"
                              + " where a.guilty_id = b.id and a.caught_by = c.id"
                              + " and a.vehicle_id = g.id and c.person_id = h.id"
                              + " and ("
                              + " concat(lower(b.last_name), lower(b.first_name), lower(b.identification_number), date_format(b.born, '%d-%m-%Y')) like %?1%"
                              + " or concat(lower(h.last_name), lower(b.first_name), lower(c.badge_number)) like %?1%"
                              + " or concat(lower(g.vin), lower(g.reg_number)) like %?1%"
                              + " or lower(f.title) like %?1%)";

    @Query(value = "select a.*" + QUERY_BASE, nativeQuery = true)
    Iterable<ViolationDbo> findByAll(String search);

    @Query(value = "select count(1)" + QUERY_BASE, nativeQuery = true)
    long countBySearch(String search);

    @Query(value = "select a.*" + QUERY_BASE, countQuery = "select count(1)" + QUERY_BASE, nativeQuery = true)
    Page<ViolationDbo> findByAll(String search, Pageable pageable);

    String QUERY_GEN_MOAR = "call generateViolations(?1, ?2, ?3)";

    @Modifying
    @Query(value = QUERY_GEN_MOAR, nativeQuery = true)
    @Transactional
    void generateViolations(Integer limit, Date start, Date end);
}
