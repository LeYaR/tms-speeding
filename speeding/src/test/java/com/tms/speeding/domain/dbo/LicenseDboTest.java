package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LicenseDboTest {
    @Autowired
    private PersonRepository repo;

    private PersonDbo licenseOwner;
    private LicenseDbo license;

    @BeforeEach
    public void setUp() {
        this.licenseOwner = new PersonDbo("LicenseOwner", "Surname", new Date());

        this.license = new LicenseDbo();
        this.license.setPerson(this.licenseOwner);
        this.license.setExpires(new Date());
        this.license.setIssued(new Date());

        this.licenseOwner.setLicense(this.license);
    }

    @Test
    public void createLicense() {
        repo.save(licenseOwner);

        Optional<PersonDbo> findingPerson = repo.findById(this.licenseOwner.getId());
        if (findingPerson.isPresent()) {
            LicenseDbo findingLicense = findingPerson.get().getLicense();

            assertNotNull(findingLicense);

            assertThat(findingLicense.getId()).isEqualTo(this.license.getId());
            assertThat(findingLicense.getPerson()).isEqualTo(this.licenseOwner);

            assertThat(findingLicense.getExpires()).isEqualTo(this.license.getExpires());
            assertThat(findingLicense.getIssued()).isEqualTo(this.license.getIssued());

            assertThat(findingLicense.isRevoked()).isNotEqualTo(true);
            assertNull(findingLicense.getLastRevocation());
        }
    }

    @Test
    public void setLicenseProperties() {

        this.license.setRevoked(true);
        this.license.setLastRevocation(new Date());
        this.license.setLicenseNumber("123");

        repo.save(this.licenseOwner);

        Optional<PersonDbo> findingPerson = repo.findById(this.licenseOwner.getId());
        if (findingPerson.isPresent()) {

            assertThat(findingPerson.get().getLicense().getId()).isEqualTo(this.license.getId());
            assertThat(findingPerson.get().getLicense().getLicenseNumber()).isEqualTo("123");
            assertThat(findingPerson.get().getLicense().isRevoked()).isEqualTo(true);
            assertThat(findingPerson.get().getLicense().getLastRevocation()).isEqualTo
                    (this.license.getLastRevocation());
        }

    }

    @Test
    public void updateLicense() {
        repo.save(licenseOwner);

        Optional<PersonDbo> findingPerson = repo.findById(licenseOwner.getId());
        LicenseDbo newLicense = new LicenseDbo(this.licenseOwner, new Date(), new Date());

        if (findingPerson.isPresent()) {
            findingPerson.get().setLicense(newLicense);
            repo.save(findingPerson.get());
        }

        Optional<PersonDbo> newFindingPerson = repo.findById(licenseOwner.getId());
        if (newFindingPerson.isPresent()) {

            assertNotNull(newFindingPerson.get().getLicense());
            assertThat(newFindingPerson.get().getLicense()).isNotEqualTo(this.license);
            assertThat(newFindingPerson.get().getLicense()).isEqualTo(newLicense);
        }
    }

}