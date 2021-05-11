package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.DepartmentDTO;
import com.tms.speeding.entities.Department;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {
    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentDTO> getDtoList(Iterable<Department> list) {
       return ((List<Department>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    private DepartmentDTO toDto(Department entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, DepartmentDTO.class);
    }

    public Department toEntity(DepartmentDTO entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, Department.class);
    }
}
