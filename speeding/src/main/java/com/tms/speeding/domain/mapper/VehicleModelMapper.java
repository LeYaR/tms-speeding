package com.tms.speeding.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.domain.dto.VehicleModelDto;
import com.tms.speeding.domain.dbo.VehicleModelDbo;
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

    public List<VehicleModelDto> toDtoList(Iterable<VehicleModelDbo> list) {
       return ((List<VehicleModelDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleModelDto toDto(VehicleModelDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(VehicleModelDbo.class, VehicleModelDto.class)
        .addMappings(m -> m.map(src -> src.getMark().getId(), VehicleModelDto::setMark));
		return mapper.map(entity, VehicleModelDto.class);
    }

    public VehicleModelDbo toEntity(VehicleModelDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        VehicleModelDbo result = mapper.map(entity, VehicleModelDbo.class);
        if (entity.getMark() != null) {
            result.setMark(vRepository.findById(entity.getMark()).orElse(null));
        }        
		return result;
    }
}