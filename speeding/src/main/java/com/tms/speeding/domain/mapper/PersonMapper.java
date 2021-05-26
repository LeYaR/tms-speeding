package com.tms.speeding.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.dbo.PersonDbo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
   
    private final ModelMapper mapper;

    public PersonMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<PersonDto> toDtoList(Iterable<PersonDbo> list) {
       return ((List<PersonDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public PersonDto toDto(PersonDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, PersonDto.class);
    }

    public PersonDbo toEntity(PersonDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, PersonDbo.class);
    }
}
