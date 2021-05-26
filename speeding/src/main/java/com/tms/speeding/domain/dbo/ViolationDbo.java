package com.tms.speeding.domain.dbo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sv_violations")
public class ViolationDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "violation_date", nullable = false)
    private Date violationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private RegionDbo region;

    @Column(name = "speed_limit", nullable = false)
    private Integer speedLimit;

    @Column(name = "actual_speed", nullable = false)
    private Integer actualSpeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guilty_id", referencedColumnName = "id", nullable = false)
    private PersonDbo guilty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private VehicleDbo vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caught_by", referencedColumnName = "id", nullable = false)
    private InspectorDbo inspector;

    @Column(name = "is_repaid")
    private boolean isRepaid;

    @Column(length = 500)
    private String note;

    public ViolationDbo(Date violationDate, int speedLimit, int actualSpeed, PersonDbo guilty, VehicleDbo vehicle,
                        InspectorDbo inspector) {
        this.violationDate = violationDate;
        this.speedLimit = speedLimit;
        this.actualSpeed = actualSpeed;
        this.guilty = guilty;
        this.vehicle = vehicle;
        this.inspector = inspector;
    }

    public ViolationDbo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(Date violationDate) {
        this.violationDate = violationDate;
    }

    public RegionDbo getRegion() {
        return region;
    }

    public void setRegion(RegionDbo region) {
        this.region = region;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Integer getActualSpeed() {
        return actualSpeed;
    }

    public void setActualSpeed(Integer actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    public PersonDbo getGuilty() {
        return guilty;
    }

    public void setGuilty(PersonDbo guilty) {
        this.guilty = guilty;
    }

    public VehicleDbo getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDbo vehicle) {
        this.vehicle = vehicle;
    }

    public InspectorDbo getInspector() {
        return inspector;
    }

    public void setInspector(InspectorDbo inspector) {
        this.inspector = inspector;
    }

    public boolean isRepaid() {
        return isRepaid;
    }

    public void setRepaid(boolean isRepaid) {
        this.isRepaid = isRepaid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
