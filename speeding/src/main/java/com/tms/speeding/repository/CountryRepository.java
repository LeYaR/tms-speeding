package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.CountryDbo;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<CountryDbo, Integer> {

    CountryDbo findByTitle(String title);
}
