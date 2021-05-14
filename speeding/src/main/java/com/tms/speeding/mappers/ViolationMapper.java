package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.ViolationD;
import com.tms.speeding.entity.Violation;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleRepository;

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

    public List<ViolationD> getDtoList(Iterable<Violation> list) {
       return ((List<Violation>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public ViolationD toDto(Violation entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Violation.class, ViolationD.class)
        .addMappings(m -> m.map(src -> src.getGuilty().getId(), ViolationD::setGuilty))
        .addMappings(m -> m.map(src -> src.getInspector().getId(), ViolationD::setInspector))
        .addMappings(m -> m.map(src -> src.getVehicle().getId(), ViolationD::setVehicle))
        .addMappings(m -> m.map(src -> src.getRegion().getId(), ViolationD::setRegion));
		return modelMapper.map(entity, ViolationD.class);
    }

    public Violation toEntity(ViolationD entity) {
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