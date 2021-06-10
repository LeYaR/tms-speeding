package com.tms.speeding.service;

import com.tms.speeding.domain.dbo.InspectorDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.InspectorDto;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.mapper.InspectorMapper;
import com.tms.speeding.domain.mapper.PersonMapper;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.InspectorRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class InspectorServiceTest {

    @MockBean
    private InspectorRepository repository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private InspectorMapper mapper;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private RankRepository rankRepository;

    @MockBean
    private PersonMapper personMapper;


    private InspectorService service;

    @BeforeEach
    public void setUp() {
        service = new InspectorService(repository, personRepository, mapper,
                rankRepository, departmentRepository, personMapper);
    }

    @Test
    public void shouldFindByIdSuccessfullyTest() {
        Mockito.when(repository.findById(228)).thenReturn(Optional.of(Mockito.mock(InspectorDbo.class)));

        service.getById(228);

        verify(repository, times(1)).findById(228);
    }

    @Test
    public void assertNotNullGettedFromServiceByIdResult() {

        var inspectorDbo = Mockito.mock(InspectorDbo.class);

        Mockito.when(repository.findById(228)).thenReturn(Optional.of(inspectorDbo));
        Mockito.when(mapper.toDto(any(InspectorDbo.class))).thenReturn(Mockito.mock(InspectorDto.class));
        var result = service.getById(228);

        assertNotNull(result);
    }

    @Test
    public void shouldPassedIfSavingInspectorsEntityIsSuccessful() {

        InspectorDto inspectorDto = Mockito.mock(InspectorDto.class);
        PersonDbo personDbo = Mockito.mock(PersonDbo.class);
        PersonDto personDto = Mockito.mock(PersonDto.class);


        Mockito.when(repository.findByAll(any(InspectorDto.class))).thenReturn(Optional.empty());
        Mockito.when(personRepository.findByAll(personDto)).thenReturn(Optional.of(personDbo));

        Mockito.when(inspectorDto.getId()).thenReturn(1);
        Mockito.when(inspectorDto.getBadgeNumber()).thenReturn("stringResponse");
        Mockito.when(inspectorDto.getPerson()).thenReturn(personDto);
        Mockito.when(inspectorDto.getRank()).thenReturn(null);
        Mockito.when(inspectorDto.getDepartment()).thenReturn(null);

        Mockito.when(personDbo.getId()).thenReturn(1);

        var response = service.save(inspectorDto);

        verify(repository, times(1)).save(any());
        assertTrue(response.isSuccess());
    }

    @Test
    public void shouldPassedIfSuccessfullyReturnsStringQuery() {
        InspectorDbo inspectorDbo = Mockito.mock(InspectorDbo.class);

        when(repository.findByAll("query")).thenReturn(List.of(inspectorDbo));

        var response = service.getAllByString("query");

        verify(repository, times(1)).findByAll(anyString());
        assertTrue(response.isSuccess());
    }
}
