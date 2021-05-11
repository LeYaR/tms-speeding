package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.PersonDTO;
import com.tms.speeding.entities.Person;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {
   
    @Autowired
    private ModelMapper modelMapper;

    public List<PersonDTO> getDtoList(Iterable<Person> list) {
       return ((List<Person>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public PersonDTO toDto(Person entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, PersonDTO.class);
    }

    public Person toEntity(PersonDTO entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, Person.class);
    }
}
