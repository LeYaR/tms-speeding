package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RegionDTO;
import com.tms.speeding.entities.Region;
import com.tms.speeding.repos.CountryRepository;

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

    public List<RegionDTO> getDtoList(Iterable<Region> list) {
       return ((List<Region>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public RegionDTO toDto(Region entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Region.class, RegionDTO.class)
        .addMappings(m -> m.map(src -> src.getCountry().getId(), RegionDTO::setCountry));
		return modelMapper.map(entity, RegionDTO.class);
    }

    public Region toEntity(RegionDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Region result = modelMapper.map(entity, Region.class);
        if (entity.getCountry() != null) {
            result.setCountry(countryRepository.findById(entity.getCountry()).orElse(null));
        }        
		return result;
    }
}