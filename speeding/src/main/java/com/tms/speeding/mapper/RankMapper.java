package com.tms.speeding.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RankD;
import com.tms.speeding.entity.Rank;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {

    private final ModelMapper mapper;

    public RankMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<RankD> toDtoList(Iterable<Rank> list) {
       return ((List<Rank>) list).stream().map(this::toDto).collect(Collectors.toList());
	}

    private RankD toDto(Rank entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, RankD.class);
    }

    public Rank toEntity(RankD entity) { 
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return mapper.map(entity, Rank.class);
    }
}
