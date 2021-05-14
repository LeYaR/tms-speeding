package com.tms.speeding.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="sv_drivers_licenses")
public class License {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @NonNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @Column(name = "license_number", length = 30)
    private String licenseNumber;
    
    @Column(name="issued", nullable = false)
    private Date issued;

    @Column(name="expires", nullable = false)
    private Date expires;

    @Column(name="is_revoked")
    private boolean isRevoked;

    @Column(name="last_revocation")
    private Date lastRevocation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
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
