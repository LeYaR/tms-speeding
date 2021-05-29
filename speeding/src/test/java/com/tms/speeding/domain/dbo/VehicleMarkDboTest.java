package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.VehicleMarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleMarkDboTest {

    @Autowired
    private VehicleMarkRepository repo;

    private VehicleMarkDbo vehicleMark;

    @BeforeEach
    public void setUp() {
        this.vehicleMark = new VehicleMarkDbo("emptyTitle");
        this.vehicleMark.setTitle("Audi");
    }

    @Test
    public void createVehicleMarkTest() {
        repo.save(this.vehicleMark);

        Optional<VehicleMarkDbo> findingMark = repo.findById(this.vehicleMark.getId());
        if (findingMark.isPresent()) {
            assertNotNull(findingMark.get().getId());
            assertThat(findingMark.get().getId()).isEqualTo(this.vehicleMark.getId());
            assertThat(findingMark.get().getTitle()).isEqualTo(this.vehicleMark.getTitle());
        }
    }

    @Test
    public void setModelsToMarkTest() {
        VehicleModelDbo firstTestModel = new VehicleModelDbo("A8", this.vehicleMark);
        VehicleModelDbo secondTestModel = new VehicleModelDbo("A6", this.vehicleMark);

        List<VehicleModelDbo> modelList = new ArrayList<>();
        modelList.add(firstTestModel);
        modelList.add(secondTestModel);

        this.vehicleMark.setModels(modelList);

        repo.save(this.vehicleMark);

        Optional<VehicleMarkDbo> findingMark = repo.findById(this.vehicleMark.getId());
        if (findingMark.isPresent()) {
            VehicleMarkDbo findingMarkDbo = findingMark.get();

            assertThat(findingMarkDbo.getModels().get(0).getTitle()).isEqualTo(
                    this.vehicleMark.getModels().get(0).getTitle());
            assertThat(findingMarkDbo.getModels().get(0).getTitle()).isNotEqualTo(
                    secondTestModel.getTitle());
            assertThat(findingMarkDbo.getModels().get(1).getTitle()).isEqualTo(
                    this.vehicleMark.getModels().get(1).getTitle());
            assertThat(findingMarkDbo.getModels().get(1).getTitle()).isNotEqualTo(
                    firstTestModel.getTitle());
        }

    }

    @Test
    public void deleteMarkTest() {
        repo.save(this.vehicleMark);

        repo.delete(this.vehicleMark);

        Optional<VehicleMarkDbo> findingMark = repo.findById(this.vehicleMark.getId());
        findingMark.ifPresent(vehicleMarkDbo -> assertThat(vehicleMarkDbo).isNotEqualTo(this.vehicleMark));
    }


}