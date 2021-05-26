package com.tms.speeding.domain.dto;

public class DirectoryDto {
    Iterable<CountryDto> countries;
    Iterable<RegionDto> regions;
    Iterable<VehicleMarkDto> marks;
    Iterable<VehicleModelDto> models;
    Iterable<RankDto> ranks;
    Iterable<DepartmentDto> departments;
    Iterable<CountryDto> countriesAll;
    Iterable<VehicleMarkDto> marksAll;

    public Iterable<CountryDto> getCountries() {
        return countries;
    }
    public void setCountries(Iterable<CountryDto> countries) {
        this.countries = countries;
    }
    public Iterable<RegionDto> getRegions() {
        return regions;
    }
    public void setRegions(Iterable<RegionDto> regions) {
        this.regions = regions;
    }
    public Iterable<VehicleMarkDto> getMarks() {
        return marks;
    }
    public void setMarks(Iterable<VehicleMarkDto> marks) {
        this.marks = marks;
    }
    public Iterable<VehicleModelDto> getModels() {
        return models;
    }
    public void setModels(Iterable<VehicleModelDto> models) {
        this.models = models;
    }
    public Iterable<RankDto> getRanks() {
        return ranks;
    }
    public void setRanks(Iterable<RankDto> ranks) {
        this.ranks = ranks;
    }
    public Iterable<DepartmentDto> getDepartments() {
        return departments;
    }
    public void setDepartments(Iterable<DepartmentDto> departments) {
        this.departments = departments;
    }
    public Iterable<CountryDto> getCountriesAll() {
        return countriesAll;
    }
    public void setCountriesAll(Iterable<CountryDto> countriesAll) {
        this.countriesAll = countriesAll;
    }
    public Iterable<VehicleMarkDto> getMarksAll() {
        return marksAll;
    }
    public void setMarksAll(Iterable<VehicleMarkDto> marksAll) {
        this.marksAll = marksAll;
    }

}
