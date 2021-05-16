package com.tms.speeding.dbo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sv_regions")
public class RegionDbo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryDbo country;

    @OneToMany(mappedBy = "region")
    private List<VehicleDbo> vehicles;

    @OneToMany(mappedBy = "region")
    private List<ViolationDbo> violations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CountryDbo getCountry() {
        return country;
    }

    public void setCountry(CountryDbo country) {
        this.country = country;
    }

    public List<VehicleDbo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDbo> vehicles) {
        this.vehicles = vehicles;
    }

    public List<ViolationDbo> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDbo> violations) {
        this.violations = violations;
    } 
    
}
