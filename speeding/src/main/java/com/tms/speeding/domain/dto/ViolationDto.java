package com.tms.speeding.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ViolationDto {
    private Integer id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date violationDate;
    private Integer region;
    private Integer speedLimit;
    private Integer actualSpeed;
    private PersonDto guilty;
    private VehicleDto vehicle;
    private InspectorDto inspector;
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
    public PersonDto getGuilty() {
        return guilty;
    }
    public void setGuilty(PersonDto guilty) {
        this.guilty = guilty;
    }
    public VehicleDto getVehicle() {
        return vehicle;
    }
    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
    }
    public InspectorDto getInspector() {
        return inspector;
    }
    public void setInspector(InspectorDto inspector) {
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
