package com.tms.speeding.mapper;

import com.tms.speeding.domain.dbo.VehicleDbo;
import com.tms.speeding.domain.dbo.VehicleModelDbo;
import com.tms.speeding.domain.dto.VehicleDto;
import com.tms.speeding.domain.dto.VehicleModelDto;
import com.tms.speeding.domain.mapper.CountryMapper;
import com.tms.speeding.domain.mapper.VehicleMapper;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleModelRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class VehicleMapperTest {
    @MockBean
    private RegionRepository regionRepository;

    @MockBean
    private VehicleModelRepository vehicleModelRepository;

    private final VehicleMapper mapper = new VehicleMapper(new ModelMapper(), regionRepository, vehicleModelRepository);
    private VehicleDbo vehicleDbo;
    private VehicleDto vehicleDto;

    @Test
    void toDtoList() {
        vehicleDbo = new VehicleDbo("number", "vin");
        vehicleDbo.setId(1);
        var dtoList = mapper.toDtoList(List.of(vehicleDbo));

        vehicleDto = dtoList.get(0);

        boolean isEqual = (Objects.equals(vehicleDbo.getRegNumber(), vehicleDto.getRegNumber()) &&
                Objects.equals(vehicleDbo.getVin(), vehicleDto.getVin()) &&
                Objects.equals(vehicleDbo.getId(), vehicleDto.getId()));

        assertTrue(isEqual);
    }

    @Test
    void toDto() {
        vehicleDbo = new VehicleDbo("number", "vin");
        vehicleDbo.setId(1);

        vehicleDto = mapper.toDto(vehicleDbo);

        boolean isEqual = (Objects.equals(vehicleDbo.getRegNumber(), vehicleDto.getRegNumber()) &&
                Objects.equals(vehicleDbo.getVin(), vehicleDto.getVin()) &&
                Objects.equals(vehicleDbo.getId(), vehicleDto.getId()));

        assertTrue(isEqual);
    }

    @Test
    void toEntity() {
        vehicleDto = new VehicleDto();
        vehicleDto.setVin("vin");
        vehicleDto.setRegNumber("numb");
        vehicleDto.setId(1);

        vehicleDbo=mapper.toEntity(vehicleDto);

        boolean isEqual = (Objects.equals(vehicleDbo.getRegNumber(), vehicleDto.getRegNumber()) &&
                Objects.equals(vehicleDbo.getVin(), vehicleDto.getVin()) &&
                Objects.equals(vehicleDbo.getId(), vehicleDto.getId()));

        assertTrue(isEqual);
    }
}