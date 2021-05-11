package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.ViolationDTO;
import com.tms.speeding.entities.Violation;
import com.tms.speeding.repos.PersonRepository;
import com.tms.speeding.repos.RegionRepository;
import com.tms.speeding.repos.VehicleRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViolationMapper {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<ViolationDTO> getDtoList(Iterable<Violation> list) {
       return ((List<Violation>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public ViolationDTO toDto(Violation entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Violation.class, ViolationDTO.class)
        .addMappings(m -> m.map(src -> src.getGuilty().getId(), ViolationDTO::setGuilty))
        .addMappings(m -> m.map(src -> src.getInspector().getId(), ViolationDTO::setInspector))
        .addMappings(m -> m.map(src -> src.getVehicle().getId(), ViolationDTO::setVehicle))
        .addMappings(m -> m.map(src -> src.getRegion().getId(), ViolationDTO::setRegion));
		return modelMapper.map(entity, ViolationDTO.class);
    }

    public Violation toEntity(ViolationDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Violation result = modelMapper.map(entity, Violation.class);
        if (entity.getGuilty() != null) {
            result.setGuilty(personRepository.findById(entity.getGuilty()).orElse(null));
        }
        if (entity.getInspector() != null) {
            result.setInspector(personRepository.findById(entity.getInspector()).orElse(null));
        }
        if (entity.getVehicle() != null) {
            result.setVehicle(vehicleRepository.findById(entity.getVehicle()).orElse(null));
        }
        if (entity.getRegion() != null) {
            result.setRegion(regionRepository.findById(entity.getRegion()).orElse(null));
        }
        
		return result;
    }
}