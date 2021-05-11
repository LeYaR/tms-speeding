package com.tms.speeding.entities;

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
@Table(name="sv_people")
public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name="first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name="middle_name", length = 50, nullable = false)
    private String middleName;

    @Column(name="born")
    private Date bornDate;

    @Column(name="identification_number", length = 14)
    private String personalNumber;

    //@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private Inspector inspector;

    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private License license;

    @OneToMany(mappedBy = "guilty")
    private List<Violation> violations;

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

    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

        
}
