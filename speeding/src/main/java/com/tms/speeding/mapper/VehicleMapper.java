package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.VehicleDto;
import com.tms.speeding.dbo.VehicleDbo;
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

    public List<VehicleDto> toDtoList(Iterable<VehicleDbo> list) {
       return ((List<VehicleDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public VehicleDto toDto(VehicleDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(VehicleDbo.class, VehicleDto.class)
        .addMappings(m -> m.map(src -> src.getRegion().getId(), VehicleDto::setRegion))
        .addMappings(m -> m.map(src -> src.getModel().getId(), VehicleDto::setModel));
		return mapper.map(entity, VehicleDto.class);
    }

    public VehicleDbo toEntity(VehicleDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        VehicleDbo result = mapper.map(entity, VehicleDbo.class);
        if (entity.getRegion() != null) {
            result.setRegion(rRepository.findById(entity.getRegion()).orElse(null));
        }
        if (entity.getModel() != null) {
            result.setModel(mRepository.findById(entity.getModel()).orElse(null));
        }        
		return result;
    }
}