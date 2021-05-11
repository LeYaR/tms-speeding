package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.InspectorDTO;
import com.tms.speeding.entities.Inspector;
import com.tms.speeding.repos.DepartmentRepository;
import com.tms.speeding.repos.PersonRepository;
import com.tms.speeding.repos.RankRepository;

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

    public List<InspectorDTO> getDtoList(Iterable<Inspector> list) {
       return ((List<Inspector>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    public InspectorDTO toDto(Inspector entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Inspector.class, InspectorDTO.class)
        .addMappings(m -> m.map(src -> src.getPerson().getId(), InspectorDTO::setPerson))
        .addMappings(m -> m.map(src -> src.getRank().getId(), InspectorDTO::setRank))
        .addMappings(m -> m.map(src -> src.getDepartment().getId(), InspectorDTO::setDepartment));
		return modelMapper.map(entity, InspectorDTO.class);
    }

    public Inspector toEntity(InspectorDTO entity) {
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
