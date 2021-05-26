package com.tms.speeding.repository;

import java.util.Optional;

import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.dbo.PersonDbo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends PagingAndSortingRepository<PersonDbo, Integer> {

     String QUERY_BASE = " from sv_people where"
                        + " lower(last_name) like %?1%"
                        + " or lower(first_name) like %?1%"
                        + " or lower(middle_name) like %?1%"
                        + " or lower(identification_number) like %?1%"
                        + " or date_format(born, '%d-%m-%Y') like %?1%";
    
     String QUERY_CHECK = "select * from sv_people where"
                        + " lower(last_name) = lower(?#{#person.lastName})"
                        + " and lower(first_name) = lower(?#{#person.firstName})"
                        + " and date_format(born, '%d-%m-%Y') = date_format(?#{#person.bornDate}, '%d-%m-%Y')"
                        + " or lower(identification_number) = lower(?#{#person.personalNumber})"
                        + " or id = ?#{#person.id} limit 1";

    @Query(value = "select *" + QUERY_BASE, nativeQuery = true)
    Iterable<PersonDbo> findByAll(String search);

    @Query(value = "select count(1)" + QUERY_BASE, nativeQuery = true)
    long countBySearch(String search);

    @Query(value = "select *" + QUERY_BASE, countQuery = "select count(1)" + QUERY_BASE, nativeQuery = true)
    Page<PersonDbo> findByAll(String search, Pageable pageable);

    @Query(value = QUERY_CHECK, nativeQuery = true)
    Optional<PersonDbo> findByAll(@Param("person") PersonDto person);

}
