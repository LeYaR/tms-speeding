package com.tms.speeding.service;

import com.tms.speeding.domain.dbo.*;
import com.tms.speeding.domain.dto.DirectoryDto;
import com.tms.speeding.domain.dto.RegionDto;
import com.tms.speeding.domain.mapper.*;
import com.tms.speeding.repository.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class DirectoryServiceTest {

    @MockBean
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;
    @MockBean
    private CountryDbo countryDbo;

    @MockBean
    private RegionRepository regionRepository;
    @Autowired
    private RegionMapper regionMapper;

    @MockBean
    private VehicleMarkRepository markRepository;
    @Autowired
    private VehicleMarkMapper markMapper;

    @MockBean
    private VehicleModelRepository modelRepository;
    @Autowired
    private VehicleModelMapper modelMapper;

    @MockBean
    private RankRepository rankRepository;
    @Autowired
    private RankMapper rankMapper;

    @MockBean
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DirectoryService directoryService;


    private DirectoryDto directory;

    @Before
    public void setUp() {

    }

    @Test
    public void assertTrueIfSavingDataInDirectoryIsSuccessful() {

        CountryDbo country = new CountryDbo();
        country.setTitle("Country");

        RegionDbo region = new RegionDbo();
        region.setTitle("Region");

        VehicleMarkDbo vehicleMark = new VehicleMarkDbo();
        vehicleMark.setTitle("Mark");

        VehicleModelDbo vehicleModel = new VehicleModelDbo();
        vehicleModel.setTitle("Model");

        RankDbo rank = new RankDbo();
        rank.setTitle("Rank");

        DepartmentDbo department = new DepartmentDbo();
        department.setTitle("Department");

        Mockito.when(countryRepository.findAll(any(Sort.class))).thenReturn(List.of(country));
        Mockito.when(regionRepository.findAll(any(Sort.class))).thenReturn(List.of(region));
        Mockito.when(markRepository.findAll(any(Sort.class))).thenReturn(List.of(vehicleMark));
        Mockito.when(modelRepository.findAll(any(Sort.class))).thenReturn(List.of(vehicleModel));
        Mockito.when(rankRepository.findAll(any(Sort.class))).thenReturn(List.of(rank));
        Mockito.when(departmentRepository.findAll(any(Sort.class))).thenReturn(List.of(department));

        directory = directoryService.getDirectories();
        boolean isSuccessful = (Objects.equals(directory.getCountries().get(0).getTitle(), country.getTitle()) &&
                Objects.equals(directory.getDepartments().get(0).getTitle(), department.getTitle()) &&
                Objects.equals(directory.getMarks().get(0).getTitle(), vehicleMark.getTitle()) &&
                Objects.equals(directory.getModels().get(0).getTitle(), vehicleModel.getTitle()) &&
                Objects.equals(directory.getRegions().get(0).getTitle(), region.getTitle()) &&
                Objects.equals(directory.getRanks().get(0).getTitle(), rank.getTitle())
        );

        assertTrue(isSuccessful);
    }

    @Test
    public void assertTrueIfRegionSavingIsSuccessfull() {

        RegionDto region = new RegionDto();
        region.setTitle("regionTitle");
        region.setId(228);
        region.setCountry(1337);
        var regionDbo = regionMapper.toEntity(region);

        Mockito.when(regionRepository.findById(228)).thenReturn(Optional.of(regionDbo));
        Mockito.when(regionRepository.findAll(any(Sort.class))).thenReturn(List.of(regionDbo));

        List<RegionDto> savingRegionList = directoryService.save(region);

        boolean isSuccessfulSaving = (Objects.equals(savingRegionList.get(0).getTitle(), region.getTitle()) &&
                Objects.equals(savingRegionList.get(0).getId(), region.getId()));

        assertTrue(isSuccessfulSaving);
    }
}
