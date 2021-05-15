package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.PersonD;
import com.tms.speeding.entity.Person;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
   
    private final ModelMapper mapper;

    public PersonMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<PersonD> toDtoList(Iterable<Person> list) {
       return ((List<Person>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public PersonD toDto(Person entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, PersonD.class);
    }

    public Person toEntity(PersonD entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, Person.class);
    }
}
