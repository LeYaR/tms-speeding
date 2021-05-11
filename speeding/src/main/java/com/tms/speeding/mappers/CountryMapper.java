package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.CountryDTO;
import com.tms.speeding.entities.Country;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<CountryDTO> getDtoList(Iterable<Country> list) {
       return ((List<Country>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public CountryDTO toDto(Country entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, CountryDTO.class);
    }

    public Country toEntity(CountryDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(entity, Country.class);
    }
}
