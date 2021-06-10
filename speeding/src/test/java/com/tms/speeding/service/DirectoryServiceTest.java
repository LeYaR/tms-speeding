package com.tms.speeding.service;

import com.tms.speeding.domain.dbo.*;
import com.tms.speeding.domain.dto.RegionDto;
import com.tms.speeding.repository.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Access;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest
class DirectoryServiceTest {

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private RegionRepository regionRepository;

    @MockBean
    private VehicleMarkRepository markRepository;

    @MockBean
    private VehicleModelRepository modelRepository;

    @MockBean
    private RankRepository rankRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DirectoryService directoryService;

    @Test
    public void passedIfOperationOfGettingDirectoryIsSuccessful() {

        CountryDbo country = Mockito.mock(CountryDbo.class);

        RegionDbo region = Mockito.mock(RegionDbo.class);

        VehicleMarkDbo vehicleMark = Mockito.mock(VehicleMarkDbo.class);

        VehicleModelDbo vehicleModel = Mockito.mock(VehicleModelDbo.class);

        RankDbo rank = Mockito.mock(RankDbo.class);

        DepartmentDbo department = Mockito.mock(DepartmentDbo.class);


        Mockito.when(countryRepository.findAll(any(Sort.class))).thenReturn(List.of(country));
        Mockito.when(regionRepository.findAll(any(Sort.class))).thenReturn(List.of(region));
        Mockito.when(markRepository.findAll(any(Sort.class))).thenReturn(List.of(vehicleMark));
        Mockito.when(modelRepository.findAll(any(Sort.class))).thenReturn(List.of(vehicleModel));
        Mockito.when(rankRepository.findAll(any(Sort.class))).thenReturn(List.of(rank));
        Mockito.when(departmentRepository.findAll(any(Sort.class))).thenReturn(List.of(department));

        directoryService.getDirectories();

        Mockito.verify(countryRepository, Mockito.times(1)).findAll(any(Sort.class));
        Mockito.verify(regionRepository, Mockito.times(1)).findAll(any(Sort.class));
        Mockito.verify(markRepository, Mockito.times(1)).findAll(any(Sort.class));
        Mockito.verify(modelRepository, Mockito.times(1)).findAll(any(Sort.class));
        Mockito.verify(rankRepository, Mockito.times(1)).findAll(any(Sort.class));
        Mockito.verify(departmentRepository, Mockito.times(1)).findAll(any(Sort.class));
    }

    @Test
    public void passedIfVerifyingOfCallingFindByIdAndFindAllInRegionRepoIsSuccessful() {

        var regionDbo = Mockito.mock(RegionDbo.class);
        var regionDto = Mockito.mock(RegionDto.class);

        Mockito.when(regionRepository.findById(anyInt())).thenReturn(Optional.of(regionDbo));
        Mockito.when(regionRepository.findAll(any(Sort.class))).thenReturn(List.of(regionDbo));

        directoryService.save(regionDto);

        Mockito.verify(regionRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(regionRepository, Mockito.times(1)).findAll(any(Sort.class));
    }
}
