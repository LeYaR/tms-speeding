package com.tms.speeding.mapper;

import com.tms.speeding.domain.dbo.LicenseDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.LicenseDto;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.mapper.LicenseMapper;
import com.tms.speeding.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@DataJpaTest
class LicenseMapperTest {

    @MockBean
    private PersonRepository personRepository;

    private final LicenseMapper mapper = new LicenseMapper(new ModelMapper(), personRepository);

    private LicenseDbo licenseDbo;
    private LicenseDto licenseDto;

    @Test
    void toDtoList() {
        var person = new PersonDbo("first", "last", new Date());
        person.setId(2);

        licenseDbo = new LicenseDbo(person, new Date(), new Date());
        licenseDbo.setId(1);

        List<LicenseDto> dtoList = mapper.toDtoList(List.of(licenseDbo));
        licenseDto = dtoList.get(0);

        boolean isEqual = (Objects.equals(licenseDto.getId(), licenseDbo.getId()) &&
                licenseDto.getPerson().equals(2));

        assertTrue(isEqual);
    }

    @Test
    void toDto() {
        var person = new PersonDbo("first", "last", new Date());
        person.setId(2);

        licenseDbo = new LicenseDbo(person, new Date(), new Date());
        licenseDbo.setId(1);
        licenseDbo.setLicenseNumber("123");

        licenseDto = mapper.toDto(licenseDbo);


        boolean isEqual = (Objects.equals(licenseDto.getId(), licenseDbo.getId()) &&
                licenseDto.getPerson().equals(2) &&
                Objects.equals(licenseDto.getIssued(), licenseDbo.getIssued()) &&
                Objects.equals(licenseDto.getExpires(), licenseDbo.getExpires()) &&
                Objects.equals(licenseDto.getLicenseNumber(), licenseDbo.getLicenseNumber()));

        assertTrue(isEqual);
    }
}