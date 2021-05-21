package com.tms.speeding.controller;

import com.tms.speeding.dto.CountryD;
import com.tms.speeding.dto.DepartmentD;
import com.tms.speeding.dto.DirectoryD;
import com.tms.speeding.dto.RankD;
import com.tms.speeding.dto.RegionD;
import com.tms.speeding.dto.VehicleMarkD;
import com.tms.speeding.dto.VehicleModelD;
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
    public DirectoryD getAll() {
        return service.getDirectories();
    }

    @PostMapping(path = "/countries/save")
	public Iterable<CountryD> saveCountry(@RequestBody CountryD object) {
        return service.save(object);
	}

    @PostMapping(path= "/countries")
    public Iterable<CountryD> getAllCountries() {
        return service.getAllCountries();
	}

    @PostMapping(path = "/regions/save")
	public Iterable<RegionD> saveRegion(@RequestBody RegionD object) {
        return service.save(object);
	}

    @PostMapping(path= "/regions")
    public Iterable<RegionD> getAllRegions() {
        return service.getAllRegions();
	}

    @PostMapping(path= "/marks/save")
	public Iterable<VehicleMarkD> saveMark(@RequestBody VehicleMarkD object) {
        return service.save(object);
	}

    @PostMapping(path= "/marks")
    public Iterable<VehicleMarkD> getAllMarks() {
        return service.getAllMarks();
	}

    @PostMapping(path= "/models/save")
	public Iterable<VehicleModelD> saveModel(@RequestBody VehicleModelD object) {
        return service.save(object);
	}

    @PostMapping(path= "/models")
    public Iterable<VehicleModelD> getAllModels() {
        return service.getAllModels();
	}

    @PostMapping(path= "/ranks/save")
	public Iterable<RankD> saveRank(@RequestBody RankD object) {
        return service.save(object);
	}

    @PostMapping(path= "/ranks")
    public Iterable<RankD> getAllRanks() {
        return service.getAllRanks();
	}

    @PostMapping(path= "/departments/save")
	public Iterable<DepartmentD> saveDepartment(@RequestBody DepartmentD object) {
        return service.save(object);
	}

    @PostMapping(path= "/departments")
    public Iterable<DepartmentD> getAllDepartments() {
        return service.getAllDepartments();
	}
}
