package com.tms.speeding.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.domain.dto.InspectorDto;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.dto.VehicleDto;
import com.tms.speeding.domain.dto.ViolationDto;
import com.tms.speeding.domain.dbo.ViolationDbo;
import com.tms.speeding.repository.InspectorRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ViolationMapper {

    private final ModelMapper mapper;
    private final InspectorMapper iMapper;
    private final VehicleMapper vMapper;
    private final PersonMapper pMapper;
    private final InspectorRepository iRepository;
    private final PersonRepository pRepository;
    private final RegionRepository rRepository;
    private final VehicleRepository vRepository;

    public ViolationMapper(ModelMapper mapper,
                           InspectorMapper iMapper,
                           PersonMapper pMapper,
                           VehicleMapper vMapper,
                           PersonRepository pRepository,
                           InspectorRepository iRepository,
                           RegionRepository rRepository,
                           VehicleRepository vRepository) {
        this.mapper = mapper;
        this.iMapper = iMapper;
        this.pMapper = pMapper;
        this.vMapper = vMapper;
        this.iRepository = iRepository;
        this.pRepository = pRepository;
        this.rRepository = rRepository;
        this.vRepository = vRepository;
    }

    public List<ViolationDto> toDtoList(Iterable<ViolationDbo> list) {
       return ((List<ViolationDbo>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}
    

    public ViolationDto toDto(ViolationDbo entity) {
        final InspectorDto inspector = iMapper.toDto(entity.getInspector());
        final PersonDto guilty = pMapper.toDto(entity.getGuilty());
        final VehicleDto vehicle = vMapper.toDto(entity.getVehicle());
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(ViolationDbo.class, ViolationDto.class)
        .addMappings(m -> m.map(src -> inspector, ViolationDto::setInspector))
        .addMappings(m -> m.map(src -> guilty, ViolationDto::setGuilty))
        .addMappings(m -> m.map(src -> vehicle, ViolationDto::setVehicle))
        .addMappings(m -> m.map(src -> src.getRegion().getId(), ViolationDto::setRegion));
		return mapper.map(entity, ViolationDto.class);
    }

    public ViolationDbo toEntity(ViolationDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        ViolationDbo result = mapper.map(entity, ViolationDbo.class);
        if (entity.getGuilty() != null && entity.getGuilty().getId() != null) {
            result.setGuilty(pRepository.findById(entity.getGuilty().getId()).orElse(null));
        }
        if (entity.getInspector() != null && entity.getInspector().getId() != null) {
            result.setInspector(iRepository.findById(entity.getInspector().getId()).orElse(null));
        }
        if (entity.getVehicle() != null && entity.getVehicle().getId() != null) {
            result.setVehicle(vRepository.findById(entity.getVehicle().getId()).orElse(null));
        }
        if (entity.getRegion() != null) {
            result.setRegion(rRepository.findById(entity.getRegion()).orElse(null));
        }
		return result;
    }
}