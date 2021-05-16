package com.tms.speeding.dbo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="sv_inspectors")
//@SecondaryTable(name = "sv_people", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class InspectorDbo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @NonNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private PersonDbo person;

    @Column(name = "badge_number", length = 20)
    private String badgeNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentDbo department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", referencedColumnName = "id")
    private RankDbo rank;

    @OneToMany(mappedBy = "inspector")
    private List<ViolationDbo> violations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonDbo getPerson() {
        return person;
    }

    public void setPerson(PersonDbo person) {
        this.person = person;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public DepartmentDbo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDbo department) {
        this.department = department;
    }

    public RankDbo getRank() {
        return rank;
    }

    public void setRank(RankDbo rank) {
        this.rank = rank;
    }

    public List<ViolationDbo> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDbo> violations) {
        this.violations = violations;
    }

        
}
