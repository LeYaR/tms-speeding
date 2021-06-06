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
import com.tms.speeding.util.ResponseObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class InspectorServiceTest {

    @MockBean
    private InspectorRepository repository;

    @MockBean
    private PersonRepository pRepository;

    @MockBean
    private RankRepository rRepository;

    @MockBean
    private DepartmentRepository dRepository;

    @Autowired
    private InspectorMapper mapper;

    @MockBean
    private PersonMapper pMapper;

    private InspectorService service;
    private InspectorDto inspectorDto;
    private InspectorDbo inspectorDbo;

    @BeforeEach
    public void setUp() {
        service = new InspectorService(repository, pRepository, mapper, rRepository, dRepository, pMapper);
        PersonDbo injectingPerson = new PersonDbo("Firstname", "Lastname", new Date());
        PersonDto injectingPersonDto = new PersonDto();

        injectingPersonDto.setBornDate(injectingPerson.getBornDate());
        injectingPersonDto.setFirstName("Firstname");
        injectingPersonDto.setLastName("Lastname");

        inspectorDbo = new InspectorDbo(injectingPerson);
        inspectorDbo.setId(228);

        inspectorDto = new InspectorDto();
        inspectorDto.setId(228);
        inspectorDto.setPerson(injectingPersonDto);
    }

    @Test
    public void shouldFindByIdSuccessfullyTest() {
        Mockito.when(repository.findById(228)).thenReturn(Optional.of(new InspectorDbo()));

        service.getById(228);

        verify(repository, times(1)).findById(228);
    }

    @Test
    public void findingEntitiesShouldBeEqualsTest() {
        Mockito.when(repository.findById(228)).thenReturn(Optional.of(inspectorDbo));
        InspectorDto findingInspector = service.getById(228);

        boolean result = ((Objects.equals(findingInspector.getId(), inspectorDto.getId())) &&
                Objects.equals(findingInspector.getPerson().getFirstName(), inspectorDto.getPerson().getFirstName()) &&
                Objects.equals(findingInspector.getPerson().getLastName(), inspectorDto.getPerson().getLastName()));

        assertTrue(result);
    }

    @Test
    public void shouldSuccessfullySaveInspectorEntityTest() {
        PersonDbo injectingPersonDbo = new PersonDbo("Firstname", "Lastname", new Date());

        InspectorDbo inspectorDbo = new InspectorDbo(injectingPersonDbo);
        inspectorDbo.setId(228);
        inspectorDbo.setBadgeNumber("nmbr");

        InspectorDto sourceInspector = mapper.toDto(inspectorDbo);

        Mockito.when(pRepository.findByAll(sourceInspector.getPerson())).thenReturn(Optional.of(injectingPersonDbo));

        service.save(sourceInspector);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldAssertEqualsAllGettedInspectorsByStringQueryTest() {
        List<InspectorDbo> inspectorList = new ArrayList<>();
        inspectorList.add(inspectorDbo);

        when(repository.findByAll("query")).thenReturn(inspectorList);

        ResponseObject response = service.getAllByString("query");
        HashMap<String, ArrayList<InspectorDto>> responseData =
                (HashMap<String, ArrayList<InspectorDto>>) response.getData();

        InspectorDto findingInspector = responseData.get("list").get(0);
        boolean result = (Objects.equals(inspectorDbo.getId(), findingInspector.getId()) &&
                Objects.equals(inspectorDbo.getPerson().getFirstName(), findingInspector.getPerson().getFirstName()));

        verify(repository, times(1)).findByAll(anyString());
        assertTrue(result);
    }
}
