package com.tms.speeding.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.domain.dto.RegionDto;
import com.tms.speeding.domain.dbo.RegionDbo;
import com.tms.speeding.repository.CountryRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    private final ModelMapper mapper;
    private final CountryRepository cRepository;

    public RegionMapper(ModelMapper mapper, CountryRepository cRepository) {
        this.mapper = mapper;
        this.cRepository = cRepository;
    }

    public List<RegionDto> toDtoList(Iterable<RegionDbo> list) {
       return ((List<RegionDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public RegionDto toDto(RegionDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(RegionDbo.class, RegionDto.class)
        .addMappings(m -> m.map(src -> src.getCountry().getId(), RegionDto::setCountry));
		return mapper.map(entity, RegionDto.class);
    }

    public RegionDbo toEntity(RegionDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        RegionDbo result = mapper.map(entity, RegionDbo.class);
        if (entity.getCountry() != null) {
            result.setCountry(cRepository.findById(entity.getCountry()).orElse(null));
        }        
		return result;
    }
}