package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleMarkD;
import com.tms.speeding.entity.VehicleMark;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class VehicleMarkMapper {

    private final ModelMapper mapper;

    public VehicleMarkMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<VehicleMarkD> toDtoList(Iterable<VehicleMark> list) {
       return ((List<VehicleMark>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleMarkD toDto(VehicleMark entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, VehicleMarkD.class);
    }

    public VehicleMark toEntity(VehicleMarkD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(entity, VehicleMark.class);
    }
}