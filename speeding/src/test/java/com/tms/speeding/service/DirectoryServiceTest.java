//package com.tms.speeding.service;
//
//import com.tms.speeding.domain.dbo.CountryDbo;
//import com.tms.speeding.domain.dbo.VehicleModelDbo;
//import com.tms.speeding.domain.dto.*;
//import com.tms.speeding.domain.mapper.*;
//import com.tms.speeding.repository.*;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class DirectoryServiceTest {
//
//    @MockBean
//    private CountryRepository countryRepository;
//    @MockBean
//    private CountryMapper countryMapper;
//    @MockBean
//    private CountryDbo countryDbo;
//
//    @MockBean
//    private RegionRepository regionRepository;
//    @MockBean
//    private RegionMapper regionMapper;
//
//    @MockBean
//    private VehicleMarkRepository markRepository;
//    @MockBean
//    private VehicleMarkMapper markMapper;
//
//    @MockBean
//    private VehicleModelRepository modelRepository;
//    @MockBean
//    private VehicleModelMapper modelMapper;
//
//    @MockBean
//    private RankRepository rankRepository;
//    @MockBean
//    private RankMapper rankMapper;
//
//    @MockBean
//    private DepartmentRepository departmentRepository;
//    @MockBean
//    private DepartmentMapper departmentMapper;
//
//    @Autowired
//    private DirectoryService directoryService;
//
//    private DirectoryDto directory;
//
//    @Before
//    public void setUp() {
//
//    }
//
//
//    @Test
//    public void DirectoryTest() {
//
//    }
//
//    @Test
//    public void saveCountryDtoTest() {
//
//        CountryDbo countryDbo = new CountryDbo("Test", "tst");
//        List<CountryDto> countryDtos = new ArrayList<>();
//        countryDtos.add(this.countryMapper.toDto(countryDbo));
//
////        Mockito.when(directoryService.getAllCountries().iterator().hasNext()).thenReturn(false);
////        Mockito.when(countryRepository.findByTitle("ParallelX")).thenReturn(null);
//
//
//        Mockito.when(directoryService.save(this.countryMapper.toDto(countryDbo))).thenReturn(countryDtos);
//        //this.countryRepository.save(country);
//
//        Mockito.when(countryRepository.findByTitle("ParallelX")).thenReturn(countryDbo);
//
//
//    }
//}