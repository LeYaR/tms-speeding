package com.tms.speeding.service;

import java.util.List;
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
    private CountryRepository countryRepository;
    private CountryMapper countryMapper;
    private RegionRepository regionRepository;
    private RegionMapper regionMapper;
    private VehicleMarkRepository markRepository;
    private VehicleMarkMapper markMapper;
    private VehicleModelRepository modelRepository;
    private VehicleModelMapper modelMapper;
    private RankRepository rankRepository;
    private RankMapper rankMapper;
    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Autowired
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Autowired
    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Autowired
    public void setRegionMapper(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Autowired
    public void setMarkRepository(VehicleMarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Autowired
    public void setMarkMapper(VehicleMarkMapper markMapper) {
        this.markMapper = markMapper;
    }

    @Autowired
    public void setModelRepository(VehicleModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Autowired
    public void setModelMapper(VehicleModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRankRepository(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Autowired
    public void setRankMapper(RankMapper rankMapper) {
        this.rankMapper = rankMapper;
    }

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

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

    public List<CountryDto> save(CountryDto object) {
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

    public List<RegionDto> save(RegionDto object) {
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

    public List<VehicleModelDto> save(VehicleModelDto object) {
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

    public List<VehicleMarkDto> save(VehicleMarkDto object) {
        markRepository.save(markMapper.toEntity(object));
        return getAllMarks();
    }

    public List<RankDto> save(RankDto object) {
        rankRepository.save(rankMapper.toEntity(object));
        return getAllRanks();
    }

    public List<DepartmentDto> save(DepartmentDto object) {
        departmentRepository.save(departmentMapper.toEntity(object));
        return getAllDepartments();
    }

    public List<CountryDto> getAllCountries() {
        return countryMapper.toDtoList(countryRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public List<RegionDto> getAllRegions() {
        return regionMapper.toDtoList(regionRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public List<VehicleMarkDto> getAllMarks() {
        return markMapper.toDtoList(markRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public List<VehicleModelDto> getAllModels() {
        return modelMapper.toDtoList(modelRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public List<RankDto> getAllRanks() {
        return rankMapper.toDtoList(rankRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentMapper.toDtoList(departmentRepository.findAll(Sort.by(TITLE_SORT)));
    }
}
