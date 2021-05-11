package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleModelDTO;
import com.tms.speeding.entities.VehicleModel;
import com.tms.speeding.repos.VehicleMarkRepository;

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

    public List<VehicleModelDTO> getDtoList(Iterable<VehicleModel> list) {
       return ((List<VehicleModel>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public VehicleModelDTO toDto(VehicleModel entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(VehicleModel.class, VehicleModelDTO.class)
        .addMappings(m -> m.map(src -> src.getMark().getId(), VehicleModelDTO::setMark));
		return modelMapper.map(entity, VehicleModelDTO.class);
    }

    public VehicleModel toEntity(VehicleModelDTO entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        VehicleModel result = modelMapper.map(entity, VehicleModel.class);
        if (entity.getMark() != null) {
            result.setMark(markRepository.findById(entity.getMark()).orElse(null));
        }        
		return result;
    }
}