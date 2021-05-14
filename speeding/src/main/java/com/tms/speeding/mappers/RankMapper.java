package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RankD;
import com.tms.speeding.entity.Rank;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankMapper {
    @Autowired
    private ModelMapper modelMapper;

    public List<RankD> toDtoList(Iterable<Rank> list) {
       return ((List<Rank>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    private RankD toDto(Rank entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, RankD.class);
    }

    public Rank toEntity(RankD entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, Rank.class);
    }
}
