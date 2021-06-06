package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.VehicleDbo;
import com.tms.speeding.domain.dbo.VehicleMarkDbo;
import com.tms.speeding.domain.dbo.VehicleModelDbo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleModelRepositoryTest {
    @Autowired
    private VehicleModelRepository repo;
    @Autowired
    TestEntityManager entityManager;

    private VehicleModelDbo model;


    @BeforeEach
    public void setUp() {
        VehicleMarkDbo mark = entityManager.persist(new VehicleMarkDbo("Audi"));

        this.model = new VehicleModelDbo("emptyTitle", mark);
        this.model.setTitle("A8");

        List<VehicleModelDbo> modelList = new ArrayList<>();
        modelList.add(this.model);

        mark.setModels(modelList);
    }

    @Test
    public void shouldPassedIfCreatingVehicleModelIsSuccessful() {
        repo.save(this.model);

        Optional<VehicleModelDbo> findingModel = repo.findById(this.model.getId());
        if (findingModel.isPresent()) {
            assertThat(findingModel.get().getMark().getTitle()).isEqualTo(this.model.getMark().getTitle());
            assertThat(findingModel.get().getTitle()).isEqualTo(this.model.getTitle());
        }
    }

    @Test
    public void shouldPassedIfSettingVehiclesToVehicleModelsIsSuccessful() {
        List<VehicleDbo> vehicleList = new ArrayList<>();
        vehicleList.add(new VehicleDbo("testRegNumb1", "testVin1"));
        vehicleList.add(new VehicleDbo("testRegNumb2", "testVin2"));

        this.model.setVehicles(vehicleList);

        repo.save(this.model);

        Optional<VehicleModelDbo> findingModel = repo.findById(this.model.getId());
        if (findingModel.isPresent()) {
            VehicleModelDbo findingModelDbo = findingModel.get();

            assertThat(findingModelDbo.getVehicles().get(0).getRegNumber()).isEqualTo(
                    this.model.getVehicles().get(0).getRegNumber());
            assertThat(findingModelDbo.getVehicles().get(0).getVin()).isEqualTo("testVin1");
            assertThat(findingModelDbo.getVehicles().get(0)).isNotEqualTo(vehicleList.get(1));

            assertThat(findingModelDbo.getVehicles().get(1).getRegNumber()).isEqualTo("testRegNumb2");
            assertThat(findingModelDbo.getVehicles().get(1).getVin()).isEqualTo(
                    this.model.getVehicles().get(1).getVin());
        }

    }

    @Test
    public void shouldPassedIfSettingMarkToModelIsSuccessful() {
        VehicleMarkDbo mark = entityManager.persist(new VehicleMarkDbo("AudiX"));
        this.model.setMark(mark);

        repo.save(this.model);

        Optional<VehicleModelDbo> findingModel = repo.findById(this.model.getId());
        findingModel.ifPresent(vehicleModelDbo -> assertThat(vehicleModelDbo.getMark().getTitle())
                .isEqualTo("AudiX"));
    }

    @Test
    public void shouldPassedIfAddingNewModelIsSuccessful() {
        repo.save(this.model);

        VehicleMarkDbo mark = entityManager.persist(new VehicleMarkDbo("BMW"));
        List<VehicleDbo> vehicleList = new ArrayList<>();
        vehicleList.add(new VehicleDbo("B777OP", "GTR"));

        VehicleModelDbo newModel = new VehicleModelDbo();
        newModel.setMark(mark);
        newModel.setTitle("M3");
        newModel.setVehicles(vehicleList);

        repo.save(newModel);

        Optional<VehicleModelDbo> firstModel = repo.findByTitle(this.model.getTitle());
        Optional<VehicleModelDbo> secondModel = repo.findByTitle("M3");

        if (firstModel.isPresent() && secondModel.isPresent()) {
            VehicleModelDbo firstModelDbo = firstModel.get();
            VehicleModelDbo secondModelDbo = secondModel.get();

            assertThat(firstModelDbo.getMark()).isNotEqualTo(secondModelDbo.getMark());
            assertThat(firstModelDbo.getTitle()).isNotEqualTo("M3");

            assertThat(secondModelDbo.getVehicles().get(0).getRegNumber()).isNotEqualTo("Some String");
            assertThat(secondModelDbo.getVehicles().get(0).getRegNumber()).isEqualTo("B777OP");
            assertThat(secondModelDbo.getVehicles().get(0).getVin()).isEqualTo(vehicleList.get(0).getVin());

        }
    }

    @Test
    public void shouldPassedIfSettingIdIsNotSuccessful() {
        this.model.setId(228);
        repo.save(this.model);

        Optional<VehicleModelDbo> findingModel = repo.findByTitle(this.model.getTitle());
        findingModel.ifPresent(vehicleModelDbo -> assertThat(vehicleModelDbo.getId()).isNotEqualTo(228));
    }
}
