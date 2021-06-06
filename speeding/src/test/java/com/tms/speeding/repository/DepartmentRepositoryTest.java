package com.tms.speeding.repository;

import com.tms.speeding.domain.dbo.DepartmentDbo;
import com.tms.speeding.domain.dbo.InspectorDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository repo;

    private DepartmentDbo testDepartment;

    @BeforeEach
    public void setUp() {
        this.testDepartment = new DepartmentDbo("TestTitleDepartment");
    }

    @Test
    public void assertNotNullIfSuccessfulSaveCountryTest() {
        this.testDepartment.setTitle("NewTestTitleDepartment");

        DepartmentDbo testDep = repo.save(this.testDepartment);

        assertNotNull(testDep);
    }

    @Test
    public void shouldPassedIfFindingDepartmentByTitleWasSuccessful() {
        repo.save(this.testDepartment);

        DepartmentDbo findingDepartment = repo.findByTitle(this.testDepartment.getTitle());

        assertNotNull(findingDepartment);
        assertThat(findingDepartment.getTitle()).isEqualTo(this.testDepartment.getTitle());
        assertThat(findingDepartment.getId()).isEqualTo(this.testDepartment.getId());
    }

    @Test
    public void shouldPassedIfSettingDepartmentAddressIsSuccessful() {
        this.testDepartment.setAddress("TestAddress");
        repo.save(this.testDepartment);

        DepartmentDbo findingDepartment = repo.findByTitle("TestTitleDepartment");

        assertNotNull(findingDepartment);
        assertNotNull(findingDepartment.getAddress());
        assertThat(findingDepartment.getAddress()).isEqualTo(this.testDepartment.getAddress());

    }

    @Test
    public void shouldPassedIfAddingInspectorToDepartmentIsSuccessful() {

        InspectorDbo inspector = new InspectorDbo(new PersonDbo("Name", "Surname", new Date()));

        List<InspectorDbo> inspectors = new ArrayList<>();
        inspectors.add(inspector);

        this.testDepartment.setInspectors(inspectors);

        repo.save(this.testDepartment);

        DepartmentDbo findingDepartment = repo.findByTitle("TestTitleDepartment");

        assertNotNull(findingDepartment.getInspectors());
        assertNotNull(findingDepartment.getInspectors().get(0));
        assertThat(findingDepartment.getInspectors().get(0)).isEqualTo(inspector);

    }

    @Test
    public void shouldPassedIfDeletingDepartmentIsSuccessful() {
        repo.save(this.testDepartment);
        repo.delete(this.testDepartment);

        DepartmentDbo findingDepartment = repo.findByTitle(this.testDepartment.getTitle());

        assertNull(findingDepartment);
    }
}
