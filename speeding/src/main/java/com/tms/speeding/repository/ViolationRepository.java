package com.tms.speeding.repository;

import java.util.Date;

import com.tms.speeding.entity.Violation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ViolationRepository extends PagingAndSortingRepository<Violation, Number>, JpaSpecificationExecutor<Violation> {
    final String QUERY_BASE = " from sv_violations a"
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
    Iterable<Violation> findByAll(String search);

    @Query(value = "select count(1)" + QUERY_BASE, nativeQuery = true)
    long countBySearch(String search);

    @Query(value = "select a.*" + QUERY_BASE, countQuery = "select count(a.*)" + QUERY_BASE, nativeQuery = true)
    Page<Violation> findByAll(String search, Pageable pageable);

    final String QUERY_GEN = "insert into sv_violations (violation_date, region_id, speed_limit,"
                            + " actual_speed, guilty_id, vehicle_id, caught_by, is_repaid, note)"
                            + " with recursive seq as ("
                            + " select 1 lvl"
                            + " union all"
                            + " select lvl + 1 from seq where lvl < ?1"
                            + " ), rng as ("
                            + " select str_to_date(date_format(from_unixtime(a.d_start + floor(rand() * a.d_range)), '%d-%m-%Y'), '%d-%m-%Y') rand_date"
                            + " from (select unix_timestamp(str_to_date('01-01-2020', '%d-%m-%Y')) d_start,"
                            + " (unix_timestamp(str_to_date('31-01-2020', '%d-%m-%Y')) - unix_timestamp(str_to_date('01-01-2020', '%d-%m-%Y'))) d_range) a"
                            + " ), notes as ("
                            + " select null text"
                            + " union all select 'stop you there'"
                            + " union all select 'another day - another violation'"
                            + " union all select 'crazy fast'"
                            + " union all select 'pathetic note'"
                            + " union all select 'caught finally'"
                            + " union all select 'next time, buddy'"
                            + " union all select 'paid'"
                            + " union all select 'random row'"
                            + " ), pre as ("
                            + " select b.rand_date dt,"
                            + " (select id from sv_regions order by rand() limit 1) rg,"
                            + " floor(50 + rand() * 30) li,"
                            + " (select id from sv_people order by rand() limit 1) gu,"
                            + " (select id from sv_vehicles order by rand() limit 1) ve,"
                            + " round(rand()) re,"
                            + " (select text from notes order by rand() limit 1) nt"
                            + " from seq a, rng b"
                            + " ) select a.dt, a.rg, a.li, a.li + floor(a.li + rand() * 20) ac,"
                            + " a.gu, a.ve, (select id from sv_inspectors where id != a.gu order by rand() limit 1) ch,"
                            + " a.re, a.nt from pre a";

    final String QUERY_GEN_MOAR = "call generateViolations(?1, ?2, ?3)";

    @Modifying
    @Query(value = QUERY_GEN_MOAR, nativeQuery = true)
    @Transactional
    void generateViolations(Integer limit, Date start, Date end);
}
