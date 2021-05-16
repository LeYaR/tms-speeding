package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.DepartmentDto;
import com.tms.speeding.dbo.DepartmentDbo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private final ModelMapper mapper;

    public DepartmentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<DepartmentDto> toDtoList(Iterable<DepartmentDbo> list) {
       return ((List<DepartmentDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    private DepartmentDto toDto(DepartmentDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, DepartmentDto.class);
    }

    public DepartmentDbo toEntity(DepartmentDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, DepartmentDbo.class);
    }
}
