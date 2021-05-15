package com.tms.speeding.service;

import com.tms.speeding.dto.DirectoryD;
import com.tms.speeding.mappers.CountryMapper;
import com.tms.speeding.mappers.DepartmentMapper;
import com.tms.speeding.mappers.RankMapper;
import com.tms.speeding.mappers.RegionMapper;
import com.tms.speeding.mappers.VehicleMarkMapper;
import com.tms.speeding.mappers.VehicleModelMapper;
import com.tms.speeding.repository.CountryRepository;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.RankRepository;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleMarkRepository;
import com.tms.speeding.repository.VehicleModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;
    
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private VehicleMarkRepository markRepository;
    @Autowired
    private VehicleMarkMapper markMapper;

    @Autowired
    private VehicleModelRepository modelRepository;
    @Autowired
    private VehicleModelMapper modelMapper;

    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentMapper departmentMapper;

    public DirectoryD getDirectories () {
        var result = new DirectoryD();
        result.setCountries(countryMapper.toDtoList(countryRepository.findAll()));
        result.setRegions(regionMapper.toDtoList(regionRepository.findAll()));
        result.setMarks(markMapper.toDtoList(markRepository.findAll()));
        result.setModels(modelMapper.toDtoList(modelRepository.findAll()));
        result.setRanks(rankMapper.toDtoList(rankRepository.findAll()));
        result.setDepartments(departmentMapper.toDtoList(departmentRepository.findAll()));
        return result;
    }
}
