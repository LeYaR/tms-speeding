package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonDboTest {

    @Autowired
    private PersonRepository repo;

    private PersonDbo testPerson;

    @BeforeEach
    public void setUp() {
        this.testPerson = new PersonDbo("testName", "testSurname", new Date());
    }

    @Test
    public void createPersonTest() {
        this.testPerson.setPersonalNumber("123");
        this.testPerson.setMiddleName("testMiddleName");
        this.testPerson.setBornDate(new Date());
        repo.save(testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(this.testPerson.getId());
        if (findingPerson.isPresent()) {

            assertThat(findingPerson.get().getFirstName()).isEqualTo(this.testPerson.getFirstName());
            assertThat(findingPerson.get().getMiddleName()).isEqualTo(this.testPerson.getMiddleName());
            assertThat(findingPerson.get().getLastName()).isEqualTo(this.testPerson.getLastName());
            assertThat(findingPerson.get().getBornDate()).isEqualTo(this.testPerson.getBornDate());
            assertThat(findingPerson.get().getPersonalNumber()).isEqualTo(this.testPerson.getPersonalNumber());
        }
    }

    @Test
    public void addInspectorToPersonTest() {
        InspectorDbo inspector = new InspectorDbo(new PersonDbo("Inspector", "Warnike", new Date()));
        inspector.setPerson(this.testPerson);
        this.testPerson.setInspector(inspector);

        repo.save(this.testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(this.testPerson.getId());
        findingPerson.ifPresent(personDbo -> assertThat(personDbo.getInspector()).isEqualTo
                (inspector));
    }

    @Test
    public void addLicenseToPersonTest() {
        LicenseDbo license = new LicenseDbo(this.testPerson, new Date(), new Date());
        this.testPerson.setLicense(license);

        repo.save(this.testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(this.testPerson.getId());
        findingPerson.ifPresent(personDbo -> assertThat(personDbo.getLicense()).isEqualTo(license));
    }

    @Test
    public void addViolationsTest() {
        ViolationDbo violation = new ViolationDbo(new Date(), 20, 30, this.testPerson,
                new VehicleDbo("tst", "test"),
                new InspectorDbo(new PersonDbo("Inspector", "Surname", new Date())));
        List<ViolationDbo> violations = new ArrayList<>();
        violations.add(violation);

        this.testPerson.setViolations(violations);
        repo.save(this.testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(testPerson.getId());
        findingPerson.ifPresent(personDbo -> assertThat(personDbo.getViolations().get(0))
                .isEqualTo(this.testPerson.getViolations().get(0)));

    }

    @Test
    public void updatePersonTest() {
        repo.save(this.testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(this.testPerson.getId());
        if (findingPerson.isPresent()) {
            PersonDbo pers = findingPerson.get();

            pers.setLastName("newLast");
            pers.setMiddleName("newMid");
            pers.setFirstName("newF");
            pers.setPersonalNumber("228");
            pers.setLicense(new LicenseDbo(pers, new Date(), new Date()));
            pers.setInspector(new InspectorDbo(new PersonDbo("Ins", "Sur", new Date())));

            repo.save(pers);
        }

        Optional<PersonDbo> updatePerson = repo.findById(this.testPerson.getId());

        if (updatePerson.isPresent()) {
            PersonDbo updatePersonDbo = updatePerson.get();

            assertThat(updatePersonDbo.getFirstName()).isEqualTo(this.testPerson.getFirstName());
            assertThat(updatePersonDbo.getLastName()).isEqualTo(this.testPerson.getLastName());
            assertThat(updatePersonDbo.getFirstName()).isNotEqualTo("testName");
            assertThat(updatePersonDbo.getLastName()).isNotEqualTo("testSurname");
        }

    }

    @Test
    public void deletePersonTest() {
        repo.save(this.testPerson);

        repo.delete(this.testPerson);

        Optional<PersonDbo> findingPerson = repo.findById(this.testPerson.getId());
        findingPerson.ifPresent(personDbo -> assertThat(personDbo).isNotEqualTo(this.testPerson));
    }

}