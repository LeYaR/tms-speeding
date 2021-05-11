package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleDTO;
import com.tms.speeding.entities.Vehicle;
import com.tms.speeding.repos.RegionRepository;
import com.tms.speeding.repos.VehicleModelRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapper {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private VehicleModelRepository modelRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<VehicleDTO> getDtoList(Iterable<Vehicle> list) {
       return ((List<Vehicle>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public VehicleDTO toDto(Vehicle entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Vehicle.class, VehicleDTO.class)
        .addMappings(m -> m.map(src -> src.getRegion().getId(), VehicleDTO::setRegion))
        .addMappings(m -> m.map(src -> src.getModel().getId(), VehicleDTO::setModel));
		return modelMapper.map(entity, VehicleDTO.class);
    }

    public Vehicle toEntity(VehicleDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Vehicle result = modelMapper.map(entity, Vehicle.class);
        if (entity.getRegion() != null) {
            result.setRegion(regionRepository.findById(entity.getRegion()).orElse(null));
        }
        if (entity.getModel() != null) {
            result.setModel(modelRepository.findById(entity.getModel()).orElse(null));
        }        
		return result;
    }
}