package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionDboTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RegionRepository repo;

    @Test
    public void addRegionTest() {
        CountryDbo country = entityManager.persist(new CountryDbo("Test", "TST"));
        RegionDbo region = repo.save(new RegionDbo("Test", country));

        assertThat(region).isNotNull();
        assertThat(region.getId()).isGreaterThan(0);
        assertThat(region.getCountry()).isEqualTo(country);
    }

    @Test
    public void addVehiclesTest() {
        CountryDbo country = entityManager.persist(new CountryDbo("Test", "TST"));

        RegionDbo region = new RegionDbo();
        region.setTitle("Midgaard");
        region.setCountry(country);

        VehicleDbo vehicle = entityManager.persist(new VehicleDbo("test", "test"));
        List<VehicleDbo> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        region.setVehicles(vehicleList);

        RegionDbo testRegion = repo.save(region);

        assertNotNull(testRegion.getVehicles());
        assertThat(testRegion.getVehicles().get(0)).isEqualTo(vehicle);

    }

    @Test
    public void addViolationsTest() {
        CountryDbo country = entityManager.persist(new CountryDbo("Test", "TST"));

        RegionDbo region = new RegionDbo("test", country);

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
}