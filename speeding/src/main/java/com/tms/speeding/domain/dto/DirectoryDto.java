package com.tms.speeding.domain.dto;

import java.util.List;

public class DirectoryDto {
    List<CountryDto> countries;
    List<RegionDto> regions;
    List<VehicleMarkDto> marks;
    List<VehicleModelDto> models;
    List<RankDto> ranks;
    List<DepartmentDto> departments;
    List<CountryDto> countriesAll;
    List<VehicleMarkDto> marksAll;

    public List<CountryDto> getCountries() {
        return countries;
    }
    public void setCountries(List<CountryDto> countries) {
        this.countries = countries;
    }
    public List<RegionDto> getRegions() {
        return regions;
    }
    public void setRegions(List<RegionDto> regions) {
        this.regions = regions;
    }
    public List<VehicleMarkDto> getMarks() {
        return marks;
    }
    public void setMarks(List<VehicleMarkDto> marks) {
        this.marks = marks;
    }
    public List<VehicleModelDto> getModels() {
        return models;
    }
    public void setModels(List<VehicleModelDto> models) {
        this.models = models;
    }
    public List<RankDto> getRanks() {
        return ranks;
    }
    public void setRanks(List<RankDto> ranks) {
        this.ranks = ranks;
    }
    public List<DepartmentDto> getDepartments() {
        return departments;
    }
    public void setDepartments(List<DepartmentDto> departments) {
        this.departments = departments;
    }
    public List<CountryDto> getCountriesAll() {
        return countriesAll;
    }
    public void setCountriesAll(List<CountryDto> countriesAll) {
        this.countriesAll = countriesAll;
    }
    public List<VehicleMarkDto> getMarksAll() {
        return marksAll;
    }
    public void setMarksAll(List<VehicleMarkDto> marksAll) {
        this.marksAll = marksAll;
    }

}
