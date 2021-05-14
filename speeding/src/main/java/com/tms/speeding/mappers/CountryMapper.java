package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.CountryD;
import com.tms.speeding.entity.Country;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<CountryD> toDtoList(Iterable<Country> list) {
       return ((List<Country>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public CountryD toDto(Country entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, CountryD.class);
    }

    public Country toEntity(CountryD entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(entity, Country.class);
    }
}
