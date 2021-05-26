package com.tms.speeding.domain.dbo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sv_countries")
public class CountryDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 70, nullable = false)
    private String title;

    @Column(length = 3)
    private String iso;

    @OneToMany(mappedBy = "country")
    private List<RegionDbo> regions;

    public CountryDbo() {

    }

    public CountryDbo(String title, String iso) {
        this.title = title;
        this.iso = iso;
    }

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

    public List<RegionDbo> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDbo> region) {
        this.regions = region;
    }

}
