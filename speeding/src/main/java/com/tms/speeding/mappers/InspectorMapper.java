package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.InspectorD;
import com.tms.speeding.entity.Inspector;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RankRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectorMapper {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<InspectorD> toDtoList(Iterable<Inspector> list) {
       return ((List<Inspector>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public InspectorD toDto(Inspector entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Inspector.class, InspectorD.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), InspectorD::setPerson))
        .addMappings(m -> m.map(src -> src.getRank().getId(), InspectorD::setRank))
        .addMappings(m -> m.map(src -> src.getDepartment().getId(), InspectorD::setDepartment));
		return modelMapper.map(entity, InspectorD.class);
    }

    public Inspector toEntity(InspectorD entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Inspector result = modelMapper.map(entity, Inspector.class);
        if (entity.getPerson() != null) {
            result.setPerson(personRepository.findById(entity.getPerson()).orElse(null));
        }
        if (entity.getRank() != null) {
            result.setRank(rankRepository.findById(entity.getRank()).orElse(null));
        }
        if (entity.getDepartment() != null) {
            result.setDepartment(departmentRepository.findById(entity.getDepartment()).orElse(null));
        }
        
		return result;
    }
}
