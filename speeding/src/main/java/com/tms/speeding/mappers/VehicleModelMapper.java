package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleModelD;
import com.tms.speeding.entity.VehicleModel;
import com.tms.speeding.repository.VehicleMarkRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleModelMapper {

    @Autowired
    private VehicleMarkRepository markRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<VehicleModelD> toDtoList(Iterable<VehicleModel> list) {
       return ((List<VehicleModel>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public VehicleModelD toDto(VehicleModel entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(VehicleModel.class, VehicleModelD.class)
        .addMappings(m -> m.map(src -> src.getMark().getId(), VehicleModelD::setMark));
		return modelMapper.map(entity, VehicleModelD.class);
    }

    public VehicleModel toEntity(VehicleModelD entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        VehicleModel result = modelMapper.map(entity, VehicleModel.class);
        if (entity.getMark() != null) {
            result.setMark(markRepository.findById(entity.getMark()).orElse(null));
        }        
		return result;
    }
}