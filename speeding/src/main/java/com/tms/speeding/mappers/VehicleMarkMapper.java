package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleMarkDTO;
import com.tms.speeding.entities.VehicleMark;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMarkMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<VehicleMarkDTO> getDtoList(Iterable<VehicleMark> list) {
       return ((List<VehicleMark>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public VehicleMarkDTO toDto(VehicleMark entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, VehicleMarkDTO.class);
    }

    public VehicleMark toEntity(VehicleMarkDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(entity, VehicleMark.class);
    }
}