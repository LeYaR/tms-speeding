package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RegionD;
import com.tms.speeding.entity.Region;
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

    public List<RegionD> toDtoList(Iterable<Region> list) {
       return ((List<Region>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public RegionD toDto(Region entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(Region.class, RegionD.class)
        .addMappings(m -> m.map(src -> src.getCountry().getId(), RegionD::setCountry));
		return mapper.map(entity, RegionD.class);
    }

    public Region toEntity(RegionD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Region result = mapper.map(entity, Region.class);
        if (entity.getCountry() != null) {
            result.setCountry(cRepository.findById(entity.getCountry()).orElse(null));
        }        
		return result;
    }
}