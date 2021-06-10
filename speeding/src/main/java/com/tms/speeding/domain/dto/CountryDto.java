package com.tms.speeding.domain.dto;

public class CountryDto {
    private Integer id;
    private String title;
    private String iso;

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
}
