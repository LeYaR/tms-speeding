package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InspectorRepositoryTest {

    @Autowired
    private InspectorRepository repo;

    private InspectorDbo testInspector;

    @BeforeEach
    public void setUp() {
        this.testInspector = new InspectorDbo();
        this.testInspector.setPerson(new PersonDbo("Inspector", "Surname", new Date()));

    }

    @Test
    public void shouldPassedIfCreatingInspectorsWasSuccessful() {
        this.testInspector.setBadgeNumber("badge");
        repo.save(this.testInspector);

        Optional<InspectorDbo> findingInspector = repo.findById(this.testInspector.getId());

        if (findingInspector.isPresent()) {
            assertNotNull(findingInspector.get().getBadgeNumber());
            assertNull(findingInspector.get().getDepartment());
            assertThat(findingInspector.get().getBadgeNumber()).isEqualTo("badge");
        }
    }

    @Test
    public void shouldPassedIfSettingDepartmentToInspectorIsSuccessful() {
        DepartmentDbo department = new DepartmentDbo();
        department.setTitle("TestTitle");
        this.testInspector.setDepartment(department);

        repo.save(this.testInspector);
        Optional<InspectorDbo> findingInspector = repo.findById(this.testInspector.getId());

        findingInspector.ifPresent(inspectorDbo -> assertThat(inspectorDbo.getDepartment()).isEqualTo
                (this.testInspector.getDepartment()));
    }

    @Test
    public void shouldPassedIfSettingInspectorRankIsSuccessful() {
        RankDbo rank = new RankDbo("Mayor");
        this.testInspector.setRank(rank);

        repo.save(this.testInspector);

        Optional<InspectorDbo> findingInspector = repo.findById(this.testInspector.getId());
        if (findingInspector.isPresent()) {
            assertNotNull(findingInspector.get().getRank());
            assertThat(findingInspector.get().getRank()).isEqualTo(rank);
        }
    }

    @Test
    public void shouldPassedIfSettingViolationToInspectorIsSuccessful() {
        ViolationDbo violation = new ViolationDbo(new Date(), 10, 20,
                new PersonDbo("Name", "Surname", new Date()),
                new VehicleDbo("228", "test"), this.testInspector);
        List<ViolationDbo> violations = new ArrayList<>();
        violations.add(violation);

        this.testInspector.setViolations(violations);

        repo.save(this.testInspector);

        Optional<InspectorDbo> findingInspector = repo.findById(this.testInspector.getId());
        if (findingInspector.isPresent()) {
            assertNotNull(findingInspector.get().getViolations().get(0));
            assertThat(findingInspector.get().getViolations().get(0)).isEqualTo(violation);
        }
    }

    @Test
    public void shouldPassedIfInspectorDeletingIsSuccessful() {
        repo.save(this.testInspector);
        repo.delete(this.testInspector);

        Optional<InspectorDbo> findingInspector = repo.findById(this.testInspector.getId());

        findingInspector.ifPresent(Assertions::assertNull);
    }
}
