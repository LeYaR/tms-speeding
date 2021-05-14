package com.tms.speeding.entity;

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
@Table(name="sv_violations")
public class Violation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="violation_date", nullable = false)
    private Date violationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @Column(name="speed_limit", nullable = false)
    private Integer speedLimit;

    @Column(name="actual_speed", nullable = false)
    private Integer actualSpeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guilty_id", referencedColumnName = "id", nullable = false)
    private Person guilty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caught_by", referencedColumnName = "id", nullable = false)
    private Person inspector;

    @Column(name="is_repaid")
    private boolean isRepaid;

    @Column(length = 500)
    private String note;

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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
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

    public Person getGuilty() {
        return guilty;
    }

    public void setGuilty(Person guilty) {
        this.guilty = guilty;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Person getInspector() {
        return inspector;
    }

    public void setInspector(Person inspector) {
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
