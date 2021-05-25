package com.tms.speeding.repository;

import com.tms.speeding.entity.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends CrudRepository<Login, Integer> {

    String QUERY = "select * from sv_users where lower(login) = lower(?#{#login})"
            + " and lower(password) = lower(?#{#password}) limit 1";
    @Query (value = QUERY, nativeQuery = true)
    Optional<Login> checkLogin(@Param ("login") String login, @Param ("password") String password);

    @Query (value = "select * from sv_users where lower(login) = lower(?#{#login}) limit 1", nativeQuery = true)
    Optional<Login> findExisting(@Param ("login") String login);
}



















