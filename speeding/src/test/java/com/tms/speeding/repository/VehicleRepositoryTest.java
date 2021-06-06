package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    private VehicleDbo vehicle;


    @BeforeEach
    public void setUp() {
        this.vehicle = entityManager.persist(new VehicleDbo("TestReg", "TestVin"));
    }

    @Test
    public void shouldPassedIfCreatingVehicleIsSuccessful() {
        repo.save(this.vehicle);

        this.vehicle.setModel(new VehicleModelDbo("Test", new VehicleMarkDbo("Test")));

        Optional<VehicleDbo> findingVehicle = repo.findById(this.vehicle.getId());
        if (findingVehicle.isPresent()) {
            assertThat(findingVehicle.get().getRegNumber()).isEqualTo(this.vehicle.getRegNumber());
            assertThat(findingVehicle.get().getVin()).isEqualTo(this.vehicle.getVin());
        }
    }

    @Test
    public void shouldPassedIfSettingRegionToVehicleIsSuccessful() {

        RegionDbo region = new RegionDbo("CurrentRegion");
        this.vehicle.setRegion(region);

        repo.save(this.vehicle);

        Optional<VehicleDbo> findingVehicle = repo.findById(this.vehicle.getId());
        findingVehicle.ifPresent(vehicleDbo -> assertThat(vehicleDbo.getRegion().getTitle())
                .isEqualTo("CurrentRegion"));
    }

    @Test
    public void shouldPassedIfSettingModelsToVehicleIsSuccessful() {
        VehicleMarkDbo mark = new VehicleMarkDbo("LADA");
        VehicleModelDbo model = new VehicleModelDbo("Vesta", mark);

        this.vehicle.setModel(model);

        repo.save(this.vehicle);

        Optional<VehicleDbo> findingVehicle = repo.findById(this.vehicle.getId());
        findingVehicle.ifPresent(vehicleDbo -> assertThat(vehicleDbo.getModel().getTitle())
                .isEqualTo(model.getTitle()));
    }

    @Test
    public void shouldPassedIfSettingViolationsToVehicleIsSuccessful() {

        InspectorDbo inspector = entityManager.persist(new InspectorDbo(
                new PersonDbo("Inspector", "Colombo", new Date())));

        PersonDbo guilty = entityManager.persist(new PersonDbo("Mobster", "Surname", new Date()));


        List<ViolationDbo> violationList = new ArrayList<>();
        violationList.add(entityManager.persist(new ViolationDbo(new Date(), 70, 120,
                guilty, this.vehicle, inspector)));

        this.vehicle.setViolations(violationList);

        repo.save(this.vehicle);

        Optional<VehicleDbo> findingVehicle = repo.findById(this.vehicle.getId());
        if (findingVehicle.isPresent()) {
            assertThat(findingVehicle.get().getViolations().get(0).getInspector().getPerson().getFirstName())
                    .isEqualTo("Inspector");
            assertThat(findingVehicle.get().getViolations().get(0).getVehicle().getRegNumber())
                    .isEqualTo(this.vehicle.getRegNumber());
        }

    }

    @Test
    public void shouldPassedIfUpdatingVehiclePropertiesIsSuccessful() {
        repo.save(this.vehicle);

        Optional<VehicleDbo> findingVehicle = repo.findById(this.vehicle.getId());
        VehicleDbo findingVehicleDbo = null;
        if (findingVehicle.isPresent()) {
            findingVehicleDbo = findingVehicle.get();

            findingVehicleDbo.setRegNumber("newReg");
            findingVehicleDbo.setVin("newVin");
            findingVehicleDbo.setRegion(new RegionDbo("braveNewWorld"));

            repo.save(findingVehicleDbo);
        }

        Optional<VehicleDbo> updatedVehicle = repo.findById(this.vehicle.getId());
        if (updatedVehicle.isPresent()) {
            assertThat(updatedVehicle.get().getRegNumber()).isEqualTo("newReg");
            assertThat(updatedVehicle.get().getVin()).isEqualTo("newVin");
            assertThat(updatedVehicle.get().getRegion().getTitle()).isEqualTo("braveNewWorld");
        }
    }
}
