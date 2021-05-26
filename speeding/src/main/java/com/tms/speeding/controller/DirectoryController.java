package com.tms.speeding.controller;

import com.tms.speeding.domain.dto.*;
import com.tms.speeding.service.DirectoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/dirs")
public class DirectoryController {

    private final DirectoryService service;

    public DirectoryController(DirectoryService service) {
        this.service = service;
    }

    @PostMapping
    public DirectoryDto getAll() {
        return service.getDirectories();
    }

    @PostMapping(path = "/countries/save")
	public Iterable<CountryDto> saveCountry(@RequestBody CountryDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/countries")
    public Iterable<CountryDto> getAllCountries() {
        return service.getAllCountries();
	}

    @PostMapping(path = "/regions/save")
	public Iterable<RegionDto> saveRegion(@RequestBody RegionDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/regions")
    public Iterable<RegionDto> getAllRegions() {
        return service.getAllRegions();
	}

    @PostMapping(path= "/marks/save")
	public Iterable<VehicleMarkDto> saveMark(@RequestBody VehicleMarkDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/marks")
    public Iterable<VehicleMarkDto> getAllMarks() {
        return service.getAllMarks();
	}

    @PostMapping(path= "/models/save")
	public Iterable<VehicleModelDto> saveModel(@RequestBody VehicleModelDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/models")
    public Iterable<VehicleModelDto> getAllModels() {
        return service.getAllModels();
	}

    @PostMapping(path= "/ranks/save")
	public Iterable<RankDto> saveRank(@RequestBody RankDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/ranks")
    public Iterable<RankDto> getAllRanks() {
        return service.getAllRanks();
	}

    @PostMapping(path= "/departments/save")
	public Iterable<DepartmentDto> saveDepartment(@RequestBody DepartmentDto object) {
        return service.save(object);
	}

    @PostMapping(path= "/departments")
    public Iterable<DepartmentDto> getAllDepartments() {
        return service.getAllDepartments();
	}
}
