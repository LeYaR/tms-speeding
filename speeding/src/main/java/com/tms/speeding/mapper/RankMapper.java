package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RankDto;
import com.tms.speeding.dbo.RankDbo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {

    private final ModelMapper mapper;

    public RankMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<RankDto> toDtoList(Iterable<RankDbo> list) {
       return ((List<RankDbo>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    private RankDto toDto(RankDbo entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, RankDto.class);
    }

    public RankDbo toEntity(RankDto entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, RankDbo.class);
    }
}
