package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.InspectorD;
import com.tms.speeding.dto.PersonD;
import com.tms.speeding.dto.VehicleD;
import com.tms.speeding.dto.ViolationD;
import com.tms.speeding.entity.Violation;
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

    public List<ViolationD> toDtoList(Iterable<Violation> list) {
       return ((List<Violation>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}
    

    public ViolationD toDto(Violation entity) { 
        final InspectorD inspector = iMapper.toDto(entity.getInspector());
        final PersonD guilty = pMapper.toDto(entity.getGuilty());
        final VehicleD vehicle = vMapper.toDto(entity.getVehicle());
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(Violation.class, ViolationD.class)
        .addMappings(m -> m.map(src -> inspector, ViolationD::setInspector))
        .addMappings(m -> m.map(src -> guilty, ViolationD::setGuilty))
        .addMappings(m -> m.map(src -> vehicle, ViolationD::setVehicle))
        .addMappings(m -> m.map(src -> src.getRegion().getId(), ViolationD::setRegion));
		return mapper.map(entity, ViolationD.class);
    }

    public Violation toEntity(ViolationD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Violation result = mapper.map(entity, Violation.class);
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