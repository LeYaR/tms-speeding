package com.tms.speeding.service;

import java.util.Optional;


import com.tms.speeding.domain.dbo.CountryDbo;
import com.tms.speeding.domain.dbo.RegionDbo;
import com.tms.speeding.domain.dbo.VehicleMarkDbo;
import com.tms.speeding.domain.dbo.VehicleModelDbo;
import com.tms.speeding.domain.dto.CountryDto;
import com.tms.speeding.domain.dto.DepartmentDto;
import com.tms.speeding.domain.dto.DirectoryDto;
import com.tms.speeding.domain.dto.RankDto;
import com.tms.speeding.domain.dto.RegionDto;
import com.tms.speeding.domain.dto.VehicleMarkDto;
import com.tms.speeding.domain.dto.VehicleModelDto;

import com.tms.speeding.domain.mapper.CountryMapper;
import com.tms.speeding.domain.mapper.DepartmentMapper;
import com.tms.speeding.domain.mapper.RankMapper;
import com.tms.speeding.domain.mapper.RegionMapper;
import com.tms.speeding.domain.mapper.VehicleMarkMapper;
import com.tms.speeding.domain.mapper.VehicleModelMapper;
import com.tms.speeding.repository.CountryRepository;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.RankRepository;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleMarkRepository;
import com.tms.speeding.repository.VehicleModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    
    private static final String TITLE_SORT = "title";

    public DirectoryDto getDirectories () {
        var result = new DirectoryDto();
        result.setCountries(getAllCountries());
        result.setRegions(getAllRegions());
        result.setMarks(getAllMarks());
        result.setModels(getAllModels());
        result.setRanks(getAllRanks());
        result.setDepartments(getAllDepartments());
        return result;
    }

    public Iterable<CountryDto> save(CountryDto object) {
        if (object.getId() == null) {
            countryRepository.save(countryMapper.toEntity(object));
        } else {
            Optional<CountryDbo> entity = countryRepository.findById(object.getId());
            if (entity.isPresent()) {
                var country = entity.get();
                country.setTitle(object.getTitle());
                country.setIso(object.getIso());
                countryRepository.save(country);
            } else {
                countryRepository.save(countryMapper.toEntity(object));
            }
        }
        return getAllCountries();
    }

    public Iterable<RegionDto> save(RegionDto object) {
        if (object.getId() == null) {
            regionRepository.save(regionMapper.toEntity(object));
        } else {
            Optional<RegionDbo> entity = regionRepository.findById(object.getId());
            if (entity.isPresent()) {
                var region = entity.get();
                region.setTitle(object.getTitle());
                CountryDbo country = null;
                if (object.getId() != null) {
                    country = countryRepository.findById(object.getCountry()).orElse(null);
                }
                region.setCountry(country);
                regionRepository.save(region);
            } else {
                regionRepository.save(regionMapper.toEntity(object));
            }
        }
        return getAllRegions();
    }

    public Iterable<VehicleModelDto> save(VehicleModelDto object) {
        if (object.getId() == null) {
            modelRepository.save(modelMapper.toEntity(object));
        } else {
            Optional<VehicleModelDbo> entity = modelRepository.findById(object.getId());
            if (entity.isPresent()) {
                var model = entity.get();
                model.setTitle(object.getTitle());
                VehicleMarkDbo mark = null;
                if (object.getId() != null) {
                    mark = markRepository.findById(object.getMark()).orElse(null);
                }
                model.setMark(mark);
                modelRepository.save(model);
            } else {
                modelRepository.save(modelMapper.toEntity(object));
            }
        }
        return getAllModels();
    }

    public Iterable<VehicleMarkDto> save(VehicleMarkDto object) {
        markRepository.save(markMapper.toEntity(object));
        return getAllMarks();
    }

    public Iterable<RankDto> save(RankDto object) {
        rankRepository.save(rankMapper.toEntity(object));
        return getAllRanks();
    }

    public Iterable<DepartmentDto> save(DepartmentDto object) {
        departmentRepository.save(departmentMapper.toEntity(object));
        return getAllDepartments();
    }

    public Iterable<CountryDto> getAllCountries() {
        return countryMapper.toDtoList(countryRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<RegionDto> getAllRegions() {
        return regionMapper.toDtoList(regionRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<VehicleMarkDto> getAllMarks() {
        return markMapper.toDtoList(markRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<VehicleModelDto> getAllModels() {
        return modelMapper.toDtoList(modelRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<RankDto> getAllRanks() {
        return rankMapper.toDtoList(rankRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<DepartmentDto> getAllDepartments() {
        return departmentMapper.toDtoList(departmentRepository.findAll(Sort.by(TITLE_SORT)));
    }
}
