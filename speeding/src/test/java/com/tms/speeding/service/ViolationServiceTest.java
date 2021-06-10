package com.tms.speeding.service;

import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dbo.VehicleDbo;
import com.tms.speeding.domain.dbo.ViolationDbo;
import com.tms.speeding.domain.dto.*;
import com.tms.speeding.domain.mapper.ViolationMapper;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.VehicleRepository;
import com.tms.speeding.repository.ViolationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ViolationServiceTest {
    @MockBean
    private ViolationRepository repository;

    @MockBean
    private PersonRepository pRepository;

    @MockBean
    private VehicleRepository vRepository;

    @MockBean
    private ViolationMapper mapper;

    @MockBean
    ViolationDbo violationDbo;

    private ViolationService violationService;

    @BeforeEach
    public void setUp() {
        violationService = new ViolationService(repository, pRepository, vRepository, mapper);
    }

    @Test
    public void getAllTest() {
        Mockito.when(repository.findAll()).thenReturn(List.of(violationDbo));
        Mockito.when(mapper.toDtoList(List.of(violationDbo))).thenReturn(List.of(new ViolationDto()));
        Mockito.when(repository.count()).thenReturn(1L);

        var response = violationService.getAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verify(mapper, Mockito.times(1)).toDtoList(any());
        Mockito.verify(repository, Mockito.times(1)).count();

        assertTrue(response.isSuccess());
    }

    @Test
    public void passedIfSaveViolationDtoIsSuccessful() {

        var violationDto = Mockito.mock(ViolationDto.class);
        var guiltyDto = Mockito.mock(PersonDto.class);
        var inspectorDto = Mockito.mock(InspectorDto.class);
        var vehicleDto = Mockito.mock(VehicleDto.class);

        var personDbo = Mockito.mock(PersonDbo.class);
        var vehicleDbo = Mockito.mock(VehicleDbo.class);
        var violationDbo = Mockito.mock(ViolationDbo.class);

        Mockito.when(violationDto.getActualSpeed()).thenReturn(1);
        Mockito.when(violationDto.getSpeedLimit()).thenReturn(1);
        Mockito.when(violationDto.getGuilty()).thenReturn(guiltyDto);
        Mockito.when(guiltyDto.getId()).thenReturn(1);
        Mockito.when(violationDto.getInspector()).thenReturn(inspectorDto);
        Mockito.when(inspectorDto.getId()).thenReturn(1);
        Mockito.when(violationDto.getVehicle()).thenReturn(vehicleDto);
        Mockito.when(vehicleDto.getId()).thenReturn(1);

        Mockito.when(pRepository.findById(anyInt())).thenReturn(Optional.of(personDbo));
        Mockito.when(vRepository.findById(anyInt())).thenReturn(Optional.of(vehicleDbo));

        Mockito.when(mapper.toEntity(violationDto)).thenReturn(violationDbo);

        var response = violationService.save(violationDto);

        Mockito.verify(repository, Mockito.times(1)).save(any());

        assertTrue(response.isSuccess());
    }

    @Test
    public void passedIfFiltrationIsSuccessful() {  // :)
        var filter = Mockito.mock(ViolationFilter.class);
        var violationDto = Mockito.mock(ViolationDto.class);
        var spec = (Specification<ViolationDbo>) Mockito.mock(Specification.class);
        var page = (Page<ViolationDbo>) Mockito.mock(Page.class);

        Mockito.when(filter.getPage()).thenReturn(1);
        Mockito.when(filter.getLimit()).thenReturn(2);

        Mockito.when(filter.getGuilty()).thenReturn(1);
        Mockito.when(filter.getInspector()).thenReturn(1);
        Mockito.when(filter.getVehicle()).thenReturn(1);
        Mockito.when(filter.getRegion()).thenReturn(1);
        Mockito.when(filter.getDate()).thenReturn(new Date());

        Mockito.when(repository.count(any())).thenReturn(1L);
        Mockito.when(mapper.toDtoList(any())).thenReturn(List.of(violationDto));
        Mockito.when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class))).thenReturn(page);
        Mockito.when(page.getContent()).thenReturn(List.of(violationDbo));
        Mockito.when(repository.count(spec)).thenReturn(1L);

        var res = violationService.getFiltered(filter);

        Mockito.verify(repository, Mockito.times(1)).count(any());
        Mockito.verify(mapper, Mockito.times(1)).toDtoList(any());
        Mockito.verify(repository, Mockito.times(1)).findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class));

        assertTrue(res.isSuccess());
    }
}
