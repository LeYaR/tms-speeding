package com.tms.speeding.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LicenseDTO {
    private Integer id;
    private Integer person;
    private String licenseNumber;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date issued;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date expires;
    private boolean isRevoked;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date lastRevocation;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPerson() {
        return person;
    }
    public void setPerson(Integer person) {
        this.person = person;
    }
    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public Date getIssued() {
        return issued;
    }
    public void setIssued(Date issued) {
        this.issued = issued;
    }
    public Date getExpires() {
        return expires;
    }
    public void setExpires(Date expires) {
        this.expires = expires;
    }
    public boolean isRevoked() {
        return isRevoked;
    }
    public void setRevoked(boolean isRevoked) {
        this.isRevoked = isRevoked;
    }
    public Date getLastRevocation() {
        return lastRevocation;
    }
    public void setLastRevocation(Date lastRevocation) {
        this.lastRevocation = lastRevocation;
    }

    
}
