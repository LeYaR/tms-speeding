package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleModelD;
import com.tms.speeding.entity.VehicleModel;
import com.tms.speeding.repository.VehicleMarkRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class VehicleModelMapper {

    private final ModelMapper mapper;
    private final VehicleMarkRepository vRepository;

    public VehicleModelMapper(ModelMapper mapper, VehicleMarkRepository vRepository) {
        this.mapper = mapper;
        this.vRepository = vRepository;
    }

    public List<VehicleModelD> toDtoList(Iterable<VehicleModel> list) {
       return ((List<VehicleModel>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleModelD toDto(VehicleModel entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(VehicleModel.class, VehicleModelD.class)
        .addMappings(m -> m.map(src -> src.getMark().getId(), VehicleModelD::setMark));
		return mapper.map(entity, VehicleModelD.class);
    }

    public VehicleModel toEntity(VehicleModelD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        VehicleModel result = mapper.map(entity, VehicleModel.class);
        if (entity.getMark() != null) {
            result.setMark(vRepository.findById(entity.getMark()).orElse(null));
        }        
		return result;
    }
}