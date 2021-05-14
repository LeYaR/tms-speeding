package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RegionD;
import com.tms.speeding.entity.Region;
import com.tms.speeding.repository.CountryRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionMapper {

    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<RegionD> toDtoList(Iterable<Region> list) {
       return ((List<Region>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public RegionD toDto(Region entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Region.class, RegionD.class)
        .addMappings(m -> m.map(src -> src.getCountry().getId(), RegionD::setCountry));
		return modelMapper.map(entity, RegionD.class);
    }

    public Region toEntity(RegionD entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Region result = modelMapper.map(entity, Region.class);
        if (entity.getCountry() != null) {
            result.setCountry(countryRepository.findById(entity.getCountry()).orElse(null));
        }        
		return result;
    }
}