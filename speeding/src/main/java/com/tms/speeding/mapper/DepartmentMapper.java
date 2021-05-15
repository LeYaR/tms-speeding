package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.DepartmentD;
import com.tms.speeding.entity.Department;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private final ModelMapper mapper;

    public DepartmentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<DepartmentD> toDtoList(Iterable<Department> list) {
       return ((List<Department>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    private DepartmentD toDto(Department entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, DepartmentD.class);
    }

    public Department toEntity(DepartmentD entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, Department.class);
    }
}
