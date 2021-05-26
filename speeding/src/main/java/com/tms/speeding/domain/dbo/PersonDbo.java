package com.tms.speeding.domain.dbo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sv_people")
public class PersonDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "born", nullable = false)
    private Date bornDate;

    @Column(name = "identification_number", length = 14)
    private String personalNumber;

    //@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private InspectorDbo inspector;

    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private LicenseDbo license;

    @OneToMany(mappedBy = "guilty")
    private List<ViolationDbo> violations;

    public PersonDbo() {

    }

    public PersonDbo(String firstName, String lastName, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public InspectorDbo getInspector() {
        return inspector;
    }

    public void setInspector(InspectorDbo inspector) {
        this.inspector = inspector;
    }

    public LicenseDbo getLicense() {
        return license;
    }

    public void setLicense(LicenseDbo license) {
        this.license = license;
    }

    public List<ViolationDbo> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDbo> violations) {
        this.violations = violations;
    }


}
