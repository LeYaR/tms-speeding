package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.CountryDto;
import com.tms.speeding.dbo.CountryDbo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    private final ModelMapper mapper;

    public CountryMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<CountryDto> toDtoList(Iterable<CountryDbo> list) {
       return ((List<CountryDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public CountryDto toDto(CountryDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, CountryDto.class);
    }

    public CountryDbo toEntity(CountryDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(entity, CountryDbo.class);
    }
}
