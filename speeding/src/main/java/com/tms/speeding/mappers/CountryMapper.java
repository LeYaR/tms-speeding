package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.CountryD;
import com.tms.speeding.entity.Country;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    private final ModelMapper mapper;

    public CountryMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<CountryD> toDtoList(Iterable<Country> list) {
       return ((List<Country>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public CountryD toDto(Country entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, CountryD.class);
    }

    public Country toEntity(CountryD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(entity, Country.class);
    }
}
