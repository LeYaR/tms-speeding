package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.LicenseDTO;
import com.tms.speeding.entities.License;
import com.tms.speeding.repos.PersonRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseMapper {
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<LicenseDTO> getDtoList(Iterable<License> list) {
       return ((List<License>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public LicenseDTO toDto(License entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(License.class, LicenseDTO.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), LicenseDTO::setPerson));
		return modelMapper.map(entity, LicenseDTO.class);
    }

    public License toEntity(LicenseDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        License result = modelMapper.map(entity, License.class);
        if (entity.getPerson() != null) {
            result.setPerson(personRepository.findById(entity.getPerson()).orElse(null));
        }        
		return result;
    }
}
