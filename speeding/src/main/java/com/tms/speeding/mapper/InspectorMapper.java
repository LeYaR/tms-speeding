package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.InspectorD;
import com.tms.speeding.entity.Inspector;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RankRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class InspectorMapper {

    private final ModelMapper mapper;
    private final PersonRepository pRepository;
    private final RankRepository rRepository;
    private final DepartmentRepository dRepository;

    public InspectorMapper(ModelMapper mapper,
                           PersonRepository pRepository,
                           RankRepository rRepository,
                           DepartmentRepository dRepository) {
        this.mapper = mapper;
        this.pRepository = pRepository;
        this.rRepository = rRepository;
        this.dRepository = dRepository;
    }

    public List<InspectorD> toDtoList(Iterable<Inspector> list) {
       return ((List<Inspector>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    public InspectorD toDto(Inspector entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.typeMap(Inspector.class, InspectorD.class)
        .addMappings(m -> m.map(src -> src.getRank().getId(), InspectorD::setRank))
        .addMappings(m -> m.map(src -> src.getDepartment().getId(), InspectorD::setDepartment));
		return mapper.map(entity, InspectorD.class);
    }

    public Inspector toEntity(InspectorD entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        /*mapper.typeMap(InspectorD.class, Inspector.class)
        .addMappings(m -> m.map(src -> sPerson, Inspector::setPerson))
        .addMappings(m -> m.map(src -> sRank, Inspector::setRank))
        .addMappings(m -> m.map(src -> sDepartment, Inspector::setDepartment))*/
        final Inspector result = mapper.map(entity, Inspector.class);
        if (entity.getPerson() != null && entity.getPerson().getId() != null) {
            result.setPerson(pRepository.findById(entity.getPerson().getId()).orElse(null));
        }
        if (entity.getRank() != null) {
            result.setRank(rRepository.findById(entity.getRank()).orElse(null));
        }
        if (entity.getDepartment() != null) {
            result.setDepartment(dRepository.findById(entity.getDepartment()).orElse(null));
        }
		return result;
    }
}
