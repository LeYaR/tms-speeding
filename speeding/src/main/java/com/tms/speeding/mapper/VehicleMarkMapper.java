package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleMarkDto;
import com.tms.speeding.dbo.VehicleMarkDbo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class VehicleMarkMapper {

    private final ModelMapper mapper;

    public VehicleMarkMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<VehicleMarkDto> toDtoList(Iterable<VehicleMarkDbo> list) {
       return ((List<VehicleMarkDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleMarkDto toDto(VehicleMarkDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, VehicleMarkDto.class);
    }

    public VehicleMarkDbo toEntity(VehicleMarkDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(entity, VehicleMarkDbo.class);
    }
}