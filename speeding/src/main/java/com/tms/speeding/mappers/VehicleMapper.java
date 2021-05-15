package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleD;
import com.tms.speeding.entity.Vehicle;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleModelRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private final ModelMapper mapper;
    private final RegionRepository rRepository;
    private final VehicleModelRepository mRepository;

    public VehicleMapper(ModelMapper mapper, RegionRepository rRepository, VehicleModelRepository mRepository) {
        this.mapper = mapper;
        this.rRepository = rRepository;
        this.mRepository = mRepository;
    }

    public List<VehicleD> toDtoList(Iterable<Vehicle> list) {
       return ((List<Vehicle>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleD toDto(Vehicle entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(Vehicle.class, VehicleD.class)
        .addMappings(m -> m.map(src -> src.getRegion().getId(), VehicleD::setRegion))
        .addMappings(m -> m.map(src -> src.getModel().getId(), VehicleD::setModel));
		return mapper.map(entity, VehicleD.class);
    }

    public Vehicle toEntity(VehicleD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Vehicle result = mapper.map(entity, Vehicle.class);
        if (entity.getRegion() != null) {
            result.setRegion(rRepository.findById(entity.getRegion()).orElse(null));
        }
        if (entity.getModel() != null) {
            result.setModel(mRepository.findById(entity.getModel()).orElse(null));
        }        
		return result;
    }
}