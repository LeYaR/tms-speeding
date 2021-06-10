package com.tms.speeding.mapper;

import com.tms.speeding.domain.dbo.InspectorDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.InspectorDto;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.mapper.InspectorMapper;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RankRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class InspectorMapperTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private final InspectorMapper mapper = new InspectorMapper(new ModelMapper(), personRepository, rankRepository,
            departmentRepository);

    private InspectorDto inspectorDto;
    private InspectorDbo inspectorDbo;

    @Test
    public void toDtoTest() {
        inspectorDbo = new InspectorDbo(new PersonDbo("Firstname", "Lastname", new Date()));
        inspectorDbo.setId(1);
        inspectorDto = mapper.toDto(inspectorDbo);

        boolean isEqual = (Objects.equals
                (inspectorDbo.getPerson().getFirstName(), inspectorDto.getPerson().getFirstName()) &&
                Objects.equals
                        (inspectorDbo.getPerson().getLastName(), inspectorDto.getPerson().getLastName()) &&
                Objects.equals(inspectorDto.getId(), inspectorDbo.getId()));

        assertTrue(isEqual);
    }

    @Test
    public void toDboTest() {
        inspectorDto = new InspectorDto();

        var person = new PersonDto();
        person.setFirstName("first");
        person.setLastName("last");
        person.setPersonalNumber("123");
        person.setBornDate(new Date());

        inspectorDto.setPerson(person);

        inspectorDbo = mapper.toEntity(inspectorDto);

        boolean isEqual = (Objects.equals
                (inspectorDbo.getPerson().getFirstName(), inspectorDto.getPerson().getFirstName()) &&
                Objects.equals
                        (inspectorDbo.getPerson().getLastName(), inspectorDto.getPerson().getLastName()) &&
                Objects.equals(inspectorDto.getId(), inspectorDbo.getId()) &&
                Objects.equals(inspectorDbo.getPerson().getBornDate(), inspectorDto.getPerson().getBornDate()));

        assertTrue(isEqual);
    }

    @Test
    public void toDtoList() {

        inspectorDbo = new InspectorDbo(new PersonDbo("Firstname", "Lastname", new Date()));
        inspectorDbo.setId(1);
        List<InspectorDto> dtoList = mapper.toDtoList(List.of(inspectorDbo));

        inspectorDto = dtoList.get(0);

        boolean isEqual = (Objects.equals
                (inspectorDbo.getPerson().getFirstName(), inspectorDto.getPerson().getFirstName()) &&
                Objects.equals
                        (inspectorDbo.getPerson().getLastName(), inspectorDto.getPerson().getLastName()) &&
                Objects.equals(inspectorDto.getId(), inspectorDbo.getId()) &&
                Objects.equals(inspectorDbo.getPerson().getBornDate(), inspectorDto.getPerson().getBornDate()));

        assertTrue(isEqual);

    }
}