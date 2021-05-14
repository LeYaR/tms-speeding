package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.DepartmentD;
import com.tms.speeding.entity.Department;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {
    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentD> toDtoList(Iterable<Department> list) {
       return ((List<Department>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    private DepartmentD toDto(Department entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, DepartmentD.class);
    }

    public Department toEntity(DepartmentD entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, Department.class);
    }
}
