package com.tms.speeding.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ViolationD {
    private Integer id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date violationDate;
    private Integer region;
    private Integer speedLimit;
    private Integer actualSpeed;
    private PersonD guilty;
    private VehicleD vehicle;
    private InspectorD inspector;
    private boolean isRepaid;
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
    public Integer getRegion() {
        return region;
    }
    public void setRegion(Integer region) {
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
    public PersonD getGuilty() {
        return guilty;
    }
    public void setGuilty(PersonD guilty) {
        this.guilty = guilty;
    }
    public VehicleD getVehicle() {
        return vehicle;
    }
    public void setVehicle(VehicleD vehicle) {
        this.vehicle = vehicle;
    }
    public InspectorD getInspector() {
        return inspector;
    }
    public void setInspector(InspectorD inspector) {
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
