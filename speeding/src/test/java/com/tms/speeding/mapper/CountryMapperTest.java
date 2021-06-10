package com.tms.speeding.mapper;

import com.tms.speeding.domain.dbo.CountryDbo;
import com.tms.speeding.domain.dto.CountryDto;
import com.tms.speeding.domain.mapper.CountryMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountryMapperTest {

    private static final CountryMapper mapper = new CountryMapper(new ModelMapper());

    private CountryDbo countryDbo;
    private CountryDto countryDto;

    @Test
    public void toDtoTest() {
        countryDbo = new CountryDbo("Country", "CNTR");
        countryDbo.setId(1);
        countryDto = mapper.toDto(countryDbo);

        boolean isEqual = (Objects.equals(countryDto.getTitle(), countryDbo.getTitle()) &&
                Objects.equals(countryDto.getIso(), countryDbo.getIso()) &&
                Objects.equals(countryDbo.getId(), countryDto.getId()));

        assertTrue(isEqual);
    }

    @Test
    public void toDboTest() {
        countryDto = new CountryDto();
        countryDto.setTitle("Country");
        countryDto.setIso("cntr");
        countryDto.setId(1);

        countryDbo = mapper.toEntity(countryDto);

        boolean isEqual = (Objects.equals(countryDto.getTitle(), countryDbo.getTitle()) &&
                Objects.equals(countryDto.getIso(), countryDbo.getIso()) &&
                Objects.equals(countryDbo.getId(), countryDto.getId()));

        assertTrue(isEqual);
    }

    @Test
    public void toDtoList() {
        countryDbo = new CountryDbo("Country", "CNTR");
        countryDbo.setId(1);

        List<CountryDto> dtoList = mapper.toDtoList(List.of(countryDbo));
        countryDto = dtoList.get(0);

        boolean isEqual = (Objects.equals(countryDto.getTitle(), countryDbo.getTitle()) &&
                Objects.equals(countryDto.getIso(), countryDbo.getIso()) &&
                Objects.equals(countryDbo.getId(), countryDto.getId()));

        assertTrue(isEqual);

    }
}
