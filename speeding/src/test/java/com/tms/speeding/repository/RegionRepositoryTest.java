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

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RegionRepository repo;

    private CountryDbo country;
    private RegionDbo region;

    @BeforeEach
    public void setUp() {
        this.country = entityManager.persist(new CountryDbo("Test", "TST"));
        this.region = new RegionDbo("test", country);
    }

    @Test
    public void shouldPassedIfRegionCreatingIsSuccessful() {
        repo.save(this.region);
        assertThat(this.region).isNotNull();
        assertThat(this.region.getId()).isGreaterThan(0);
        assertThat(this.region.getCountry()).isEqualTo(this.country);
    }

    @Test
    public void shouldPassedIfSettingOfVehicleListToRegionIsSuccessful() {
        repo.save(this.region);
        this.region.setTitle("Midgaard");
        this.region.setCountry(this.country);

        VehicleDbo vehicle = entityManager.persist(new VehicleDbo("test", "test"));
        List<VehicleDbo> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        this.region.setVehicles(vehicleList);

        RegionDbo testRegion = repo.save(this.region);

        assertNotNull(testRegion.getVehicles());
        assertThat(testRegion.getVehicles().get(0)).isEqualTo(vehicle);
    }

    @Test
    public void shouldPassedIfSettingViolationListToRegionIsSuccessful() {

        VehicleDbo vehicle = new VehicleDbo("test", "test");
        List<VehicleDbo> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        region.setVehicles(vehicleList);

        PersonDbo person = (new PersonDbo("Name", "Surname", new Date()));
        InspectorDbo inspector = (new InspectorDbo(person));

        ViolationDbo violation = (new ViolationDbo(new Date(), 10, 20,
                person, vehicle, inspector));
        List<ViolationDbo> violations = new ArrayList<>();
        violations.add(violation);

        region.setViolations(violations);

        RegionDbo testRegion = repo.save(region);

        assertNotNull(testRegion.getViolations());
        assertThat(testRegion.getViolations().get(0)).isEqualTo(violation);
    }

    @Test
    public void shouldPassedIfDeletingRegionIsSuccessful() {
        repo.save(this.region);
        repo.delete(this.region);
        assertNull(repo.findByTitle("test"));
    }
}
