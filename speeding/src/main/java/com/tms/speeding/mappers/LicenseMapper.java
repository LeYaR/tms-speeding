package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.LicenseD;
import com.tms.speeding.entity.License;
import com.tms.speeding.repository.PersonRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class LicenseMapper {

    private final ModelMapper mapper;
    private final PersonRepository pRepository;

    public LicenseMapper(ModelMapper mapper, PersonRepository pRepository) {
        this.mapper = mapper;
        this.pRepository = pRepository;
    }

    public List<LicenseD> toDtoList(Iterable<License> list) {
       return ((List<License>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public LicenseD toDto(License entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(License.class, LicenseD.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), LicenseD::setPerson));
		return mapper.map(entity, LicenseD.class);
    }

    public License toEntity(LicenseD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        License result = mapper.map(entity, License.class);
        if (entity.getPerson() != null) {
            result.setPerson(pRepository.findById(entity.getPerson()).orElse(null));
        }        
		return result;
    }
}
