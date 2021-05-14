package com.tms.speeding.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sv_countries")
public class Country {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 70, nullable = false)
    private String title;

    @Column(length = 3)
    private String iso;

    @OneToMany(mappedBy = "country")
    private List<Region> regions;

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

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> region) {
        this.regions = region;
    }
    
}
