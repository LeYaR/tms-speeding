package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.ViolationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ViolationDboTest {
    @Autowired
    private ViolationRepository repo;

    @Autowired
    TestEntityManager entityManager;

    private ViolationDbo violation;

    @BeforeEach
    public void setUp() {
        VehicleDbo vehicle = entityManager.persist(new VehicleDbo("2089 MI-7", "vin"));

        InspectorDbo inspector = entityManager.persist(new InspectorDbo(
                new PersonDbo("Inpctr", "Colombo", new Date())));

        PersonDbo guilty = entityManager.persist(new PersonDbo("Mobster", "Surname", new Date()));


        this.violation = entityManager.persist(new ViolationDbo(new Date(), 70, 120,
                guilty, vehicle, inspector));
    }

    @Test
    public void createViolation() {
        repo.save(this.violation);

        Optional<ViolationDbo> findingViolation = repo.findById(this.violation.getId());
        if (findingViolation.isPresent()) {
            ViolationDbo findingViolationDbo = findingViolation.get();

            assertThat(findingViolationDbo.getViolationDate()).isEqualTo(this.violation.getViolationDate());
            assertThat(findingViolationDbo.getActualSpeed()).isEqualTo(120);
            assertThat(findingViolationDbo.getSpeedLimit()).isLessThan(80);

            assertThat(findingViolationDbo.getGuilty().getLastName()).isEqualTo("Surname");
            assertThat(findingViolationDbo.getInspector().getPerson().getFirstName()).isEqualTo(
                    this.violation.getInspector().getPerson().getFirstName());
            assertThat(findingViolationDbo.getVehicle().getRegNumber()).isEqualTo("2089 MI-7");
        }
    }

    @Test
    public void setRegionTest() {
        RegionDbo region = new RegionDbo("CurrentRegion");

        this.violation.setRegion(region);

        repo.save(this.violation);

        Optional<ViolationDbo> findingViolation = repo.findById(this.violation.getId());
        if (findingViolation.isPresent()) {
            assertNotNull(findingViolation.get().getRegion());
            assertThat(findingViolation.get().getRegion()).isEqualTo(this.violation.getRegion());
        }

    }

    @Test
    public void updateViolationTest() {
        repo.save(this.violation);

        Optional<ViolationDbo> findingViolation = repo.findById(this.violation.getId());
        ViolationDbo findingViolationDbo = null;
        if (findingViolation.isPresent()) {
            findingViolationDbo = findingViolation.get();

            findingViolationDbo.setViolationDate(new Date());
            findingViolationDbo.setGuilty(new PersonDbo("TestPerson", "TestSur", new Date()));
            findingViolationDbo.setInspector(new InspectorDbo(new PersonDbo("testInspector", "testSurname",
                    new Date())));
            findingViolationDbo.setSpeedLimit(100);
            findingViolationDbo.setActualSpeed(120);
            findingViolationDbo.setNote("Aggressive racer");
            findingViolationDbo.setVehicle(new VehicleDbo("1234-AB4", "vin2"));
            findingViolationDbo.setRepaid(true);

            repo.save(findingViolationDbo);
        }
        Optional<ViolationDbo> updatedViolation = repo.findById(this.violation.getId());
        if (updatedViolation.isPresent()) {
            ViolationDbo updatedViolationDbo = updatedViolation.get();

            assertNotNull(findingViolationDbo);
            assertNull(updatedViolationDbo.getRegion());
            assertThat(updatedViolationDbo.getViolationDate()).isEqualTo(this.violation.getViolationDate());
            assertThat(updatedViolationDbo.getGuilty().getFirstName()).isEqualTo("TestPerson");
            assertThat(updatedViolationDbo.getVehicle().getRegNumber()).isEqualTo("1234-AB4");
            assertNotNull(updatedViolationDbo.getNote());
        }
    }

}
