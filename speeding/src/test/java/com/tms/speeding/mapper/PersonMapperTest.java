package com.tms.speeding.mapper;

import com.tms.speeding.domain.dbo.LicenseDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonMapperTest {
    private static final PersonMapper mapper = new PersonMapper(new ModelMapper());

    private PersonDbo personDbo;
    private PersonDto personDto;

    @Test
    public void toDtoTest() {
        personDbo = new PersonDbo("Firstname", "Surname", new Date());
        personDbo.setId(1);
        personDbo.setPersonalNumber("123");
        personDbo.setMiddleName("Middle");
        personDbo.setLicense(new LicenseDbo(this.personDbo, new Date(), new Date()));

        personDto = mapper.toDto(personDbo);

        boolean isEqual = (Objects.equals(personDto.getFirstName(), personDbo.getFirstName()) &&
                Objects.equals(personDto.getLastName(), personDbo.getLastName()) &&
                Objects.equals(personDbo.getId(), personDto.getId()) &&
                Objects.equals(personDbo.getBornDate(), personDto.getBornDate()) &&
                Objects.equals(personDbo.getMiddleName(), personDto.getMiddleName()) &&
                Objects.equals(personDbo.getPersonalNumber(), personDto.getPersonalNumber()));

        assertTrue(isEqual);
    }

    @Test
    public void toDboTest() {
        personDto = new PersonDto();
        personDto.setFirstName("First");
        personDto.setLastName("Last");
        personDto.setId(1);
        personDto.setMiddleName("Middle");
        personDto.setBornDate(new Date());
        personDto.setPersonalNumber("123");

        personDbo = mapper.toEntity(personDto);

        boolean isEqual = (Objects.equals(personDto.getFirstName(), personDbo.getFirstName()) &&
                Objects.equals(personDto.getLastName(), personDbo.getLastName()) &&
                Objects.equals(personDbo.getId(), personDto.getId()) &&
                Objects.equals(personDbo.getBornDate(), personDto.getBornDate()) &&
                Objects.equals(personDbo.getMiddleName(), personDto.getMiddleName()) &&
                Objects.equals(personDbo.getPersonalNumber(), personDto.getPersonalNumber()));


        assertTrue(isEqual);
    }

    @Test
    public void toDtoList() {
        personDbo = new PersonDbo("Firstname", "Surname", new Date());
        personDbo.setId(1);
        personDbo.setPersonalNumber("123");
        personDbo.setMiddleName("Middle");
        personDbo.setLicense(new LicenseDbo(this.personDbo, new Date(), new Date()));


        List<PersonDto> dtoList = mapper.toDtoList(List.of(personDbo));
        personDto = dtoList.get(0);

        boolean isEqual = (Objects.equals(personDto.getFirstName(), personDbo.getFirstName()) &&
                Objects.equals(personDto.getLastName(), personDbo.getLastName()) &&
                Objects.equals(personDbo.getId(), personDto.getId()) &&
                Objects.equals(personDbo.getBornDate(), personDto.getBornDate()) &&
                Objects.equals(personDbo.getMiddleName(), personDto.getMiddleName()) &&
                Objects.equals(personDbo.getPersonalNumber(), personDto.getPersonalNumber()));

        assertTrue(isEqual);

    }
}
