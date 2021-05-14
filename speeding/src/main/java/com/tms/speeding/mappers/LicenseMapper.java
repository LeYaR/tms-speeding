package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.LicenseD;
import com.tms.speeding.entity.License;
import com.tms.speeding.repository.PersonRepository;

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

    public List<LicenseD> toDtoList(Iterable<License> list) {
       return ((List<License>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public LicenseD toDto(License entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(License.class, LicenseD.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), LicenseD::setPerson));
		return modelMapper.map(entity, LicenseD.class);
    }

    public License toEntity(LicenseD entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        License result = modelMapper.map(entity, License.class);
        if (entity.getPerson() != null) {
            result.setPerson(personRepository.findById(entity.getPerson()).orElse(null));
        }        
		return result;
    }
}
