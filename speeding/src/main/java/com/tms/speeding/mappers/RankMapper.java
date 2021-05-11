package com.tms.speeding.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tms.speeding.dto.RankDTO;
import com.tms.speeding.entities.Rank;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankMapper {
    @Autowired
    private ModelMapper modelMapper;

    public List<RankDTO> getDtoList(Iterable<Rank> list) {
       return ((List<Rank>) list).stream().map(this::toDto)
                .collect(Collectors.toList());
	}

    private RankDTO toDto(Rank entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, RankDTO.class);
    }

    public Rank toEntity(RankDTO entity) { 
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper.map(entity, Rank.class);
    }
}
