package com.tms.speeding.repository;

import java.util.Date;
import java.util.Optional;

import com.tms.speeding.entity.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {

    final String QUERY_BASE = " from sv_people where"
                        + " lower(last_name) like %?1%"
                        + " or lower(first_name) like %?1%"
                        + " or lower(middle_name) like %?1%"
                        + " or lower(identification_number) like %?1%"
                        + " or date_format(born, '%d-%m-%Y') like %?1%";
    
    final String QUERY_CHECK = "select * from sv_people where"
                        + " lower(last_name) = lower(:last_name)"
                        + " and lower(first_name) = lower(:first_name)"
                        + " and date_format(born, '%d-%m-%Y') = date_format(:born_date, '%d-%m-%Y')"
                        + " or lower(identification_number) = :personal_number limit 1";

    @Query(value = "select *" + QUERY_BASE, nativeQuery = true)
    Iterable<Person> findByAll(String search);

    @Query(value = "select *" + QUERY_BASE, countQuery = "select count(1)" + QUERY_BASE, nativeQuery = true)
    Page<Person> findByAll(String search, Pageable pageable);

    @Query(value = QUERY_CHECK, nativeQuery = true)
    Optional<Person> findByAll(@Param("last_name") String lastName,
                               @Param("first_name") String firstName,
                               @Param("born_date") Date bornDate,
                               @Param("personal_number") String personalNumber);

}
