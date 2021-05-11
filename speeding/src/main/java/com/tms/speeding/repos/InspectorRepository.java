package com.tms.speeding.repos;

import com.tms.speeding.entities.Inspector;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InspectorRepository extends PagingAndSortingRepository<Inspector, Integer> {
    final String QUERY_SEARCH = "select a.* from sv_inspectors a"
                                + " left join sv_ranks c on a.rank_id = c.id"
                                + " left join sv_departments d on a.department_id = d.id, sv_people b"
                                + " where a.person_id = b.id"
                                + " and (lower(b.last_name) like %?1%"
                                + " or lower(b.first_name) like %?1%"
                                + " or lower(b.middle_name) like %?1%"
                                + " or date_format(b.born, '%d-%m-%Y') like %?1%"
                                + " or lower(a.badge_number) like %?1%"
                                + " or lower(c.title) like %?1%"
                                + " or lower(d.title) like %?1%)";
    

    @Query(value = QUERY_SEARCH, nativeQuery = true)
    Iterable<Inspector> findByAll(String search);
}