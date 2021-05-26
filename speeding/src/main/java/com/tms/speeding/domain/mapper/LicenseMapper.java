package com.tms.speeding.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.domain.dto.LicenseDto;
import com.tms.speeding.domain.dbo.LicenseDbo;
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

    public List<LicenseDto> toDtoList(Iterable<LicenseDbo> list) {
       return ((List<LicenseDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public LicenseDto toDto(LicenseDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(LicenseDbo.class, LicenseDto.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), LicenseDto::setPerson));
		return mapper.map(entity, LicenseDto.class);
    }

    public LicenseDbo toEntity(LicenseDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        LicenseDbo result = mapper.map(entity, LicenseDbo.class);
        if (entity.getPerson() != null) {
            result.setPerson(pRepository.findById(entity.getPerson()).orElse(null));
        }        
		return result;
    }
}
