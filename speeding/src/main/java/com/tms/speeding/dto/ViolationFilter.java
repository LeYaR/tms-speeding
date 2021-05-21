package com.tms.speeding.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ViolationFilter {
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;
    private Integer guilty;
    private Integer inspector;
    private Integer vehicle;
    private Integer region;
    private Integer page;
    private Integer limit;
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getGuilty() {
        return guilty;
    }
    public void setGuilty(Integer guilty) {
        this.guilty = guilty;
    }
    public Integer getInspector() {
        return inspector;
    }
    public void setInspector(Integer inspector) {
        this.inspector = inspector;
    }
    public Integer getVehicle() {
        return vehicle;
    }
    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }
    public Integer getRegion() {
        return region;
    }
    public void setRegion(Integer region) {
        this.region = region;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    
        
}
