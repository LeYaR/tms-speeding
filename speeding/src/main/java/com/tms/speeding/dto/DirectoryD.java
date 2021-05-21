package com.tms.speeding.dto;

public class DirectoryD {
    Iterable<CountryD> countries;
    Iterable<RegionD> regions;
    Iterable<VehicleMarkD> marks;
    Iterable<VehicleModelD> models;
    Iterable<RankD> ranks;
    Iterable<DepartmentD> departments;
    Iterable<CountryD> countriesAll;
    Iterable<VehicleMarkD> marksAll;

    public Iterable<CountryD> getCountries() {
        return countries;
    }
    public void setCountries(Iterable<CountryD> countries) {
        this.countries = countries;
    }
    public Iterable<RegionD> getRegions() {
        return regions;
    }
    public void setRegions(Iterable<RegionD> regions) {
        this.regions = regions;
    }
    public Iterable<VehicleMarkD> getMarks() {
        return marks;
    }
    public void setMarks(Iterable<VehicleMarkD> marks) {
        this.marks = marks;
    }
    public Iterable<VehicleModelD> getModels() {
        return models;
    }
    public void setModels(Iterable<VehicleModelD> models) {
        this.models = models;
    }
    public Iterable<RankD> getRanks() {
        return ranks;
    }
    public void setRanks(Iterable<RankD> ranks) {
        this.ranks = ranks;
    }
    public Iterable<DepartmentD> getDepartments() {
        return departments;
    }
    public void setDepartments(Iterable<DepartmentD> departments) {
        this.departments = departments;
    }
    public Iterable<CountryD> getCountriesAll() {
        return countriesAll;
    }
    public void setCountriesAll(Iterable<CountryD> countriesAll) {
        this.countriesAll = countriesAll;
    }
    public Iterable<VehicleMarkD> getMarksAll() {
        return marksAll;
    }
    public void setMarksAll(Iterable<VehicleMarkD> marksAll) {
        this.marksAll = marksAll;
    }

}
