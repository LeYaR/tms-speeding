package com.tms.speeding.repository;

import java.util.Optional;

import com.tms.speeding.dto.InspectorDto;
import com.tms.speeding.dbo.InspectorDbo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface InspectorRepository extends PagingAndSortingRepository<InspectorDbo, Integer> {
    final String QUERY_BASE = " from sv_inspectors a"
                                + " left join sv_ranks c on a.rank_id = c.id"
                                + " left join sv_departments d on a.department_id = d.id, sv_people b"
                                + " where a.person_id = b.id"
                                + " and (lower(b.last_name) like %?1%"
                                + " or lower(b.first_name) like %?1%"
                                + " or lower(b.identification_number) like %?1%"
                                + " or date_format(b.born, '%d-%m-%Y') like %?1%"
                                + " or lower(a.badge_number) like %?1%"
                                + " or lower(c.title) like %?1%"
                                + " or lower(d.title) like %?1%)";
    
    final String QUERY_CHECK = "select * from sv_inspectors where"
                        + " person_id = ?#{#inspector.person.id}"
                        + " or lower(badge_number) = lower(?#{#inspector.badgeNumber})"
                        + " or id = ?#{#inspector.id}";

    @Query(value = "select a.*" + QUERY_BASE, nativeQuery = true)
    Iterable<InspectorDbo> findByAll(String search);

    @Query(value = "select a.*" + QUERY_BASE, countQuery = "select count(a.*)" + QUERY_BASE, nativeQuery = true)
    Page<InspectorDbo> findByAll(String search, Pageable pageable);

    @Query(value = QUERY_CHECK, nativeQuery = true)
    Optional<InspectorDbo> findByAll(@Param("inspector") InspectorDto inspector);
}
