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
@Table(name="sv_vehicles")
public class VehicleDbo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 17)
    private String vin;

    @Column(name="reg_number", length = 10, nullable = false)
    private String regNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private VehicleModelDbo model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private RegionDbo region;

    @OneToMany(mappedBy = "vehicle")
    private List<ViolationDbo> violations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public VehicleModelDbo getModel() {
        return model;
    }

    public void setModel(VehicleModelDbo model) {
        this.model = model;
    }

    public RegionDbo getRegion() {
        return region;
    }

    public void setRegion(RegionDbo region) {
        this.region = region;
    }

    public List<ViolationDbo> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDbo> violations) {
        this.violations = violations;
    }

        
}
