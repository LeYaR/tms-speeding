package com.tms.speeding.repos;

import com.tms.speeding.entities.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
    final String QUERY_SEARCH = "select * from sv_people where"
                        + " lower(last_name) like %?1%"
                        + " or lower(first_name) like %?1%"
                        + " or lower(middle_name) like %?1%"
                        + " or lower(identification_number) like %?1%"
                        + " or date_format(born, '%d-%m-%Y') like %?1%";
    

    @Query(value = QUERY_SEARCH, nativeQuery = true)
    Iterable<Person> findByAll(String search);
}
