package com.tms.speeding.service;

import java.util.Optional;


import com.tms.speeding.dto.CountryD;
import com.tms.speeding.dto.DepartmentD;
import com.tms.speeding.dto.DirectoryD;
import com.tms.speeding.dto.RankD;
import com.tms.speeding.dto.RegionD;
import com.tms.speeding.dto.VehicleMarkD;
import com.tms.speeding.dto.VehicleModelD;
import com.tms.speeding.entity.Country;
import com.tms.speeding.entity.Region;
import com.tms.speeding.entity.VehicleMark;
import com.tms.speeding.entity.VehicleModel;
import com.tms.speeding.mapper.CountryMapper;
import com.tms.speeding.mapper.DepartmentMapper;
import com.tms.speeding.mapper.RankMapper;
import com.tms.speeding.mapper.RegionMapper;
import com.tms.speeding.mapper.VehicleMarkMapper;
import com.tms.speeding.mapper.VehicleModelMapper;
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

    public DirectoryD getDirectories () {
        var result = new DirectoryD();
        result.setCountries(getAllCountries());
        result.setRegions(getAllRegions());
        result.setMarks(getAllMarks());
        result.setModels(getAllModels());
        result.setRanks(getAllRanks());
        result.setDepartments(getAllDepartments());
        return result;
    }

    public Iterable<CountryD> save(CountryD object) {
        if (object.getId() == null) {
            countryRepository.save(countryMapper.toEntity(object));
        } else {
            Optional<Country> entity = countryRepository.findById(object.getId());
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

    public Iterable<RegionD> save(RegionD object) {
        if (object.getId() == null) {
            regionRepository.save(regionMapper.toEntity(object));
        } else {
            Optional<Region> entity = regionRepository.findById(object.getId());
            if (entity.isPresent()) {
                var region = entity.get();
                region.setTitle(object.getTitle());
                Country country = null;
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

    public Iterable<VehicleModelD> save(VehicleModelD object) {
        if (object.getId() == null) {
            modelRepository.save(modelMapper.toEntity(object));
        } else {
            Optional<VehicleModel> entity = modelRepository.findById(object.getId());
            if (entity.isPresent()) {
                var model = entity.get();
                model.setTitle(object.getTitle());
                VehicleMark mark = null;
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

    public Iterable<VehicleMarkD> save(VehicleMarkD object) {
        markRepository.save(markMapper.toEntity(object));
        return getAllMarks();
    }

    public Iterable<RankD> save(RankD object) {
        rankRepository.save(rankMapper.toEntity(object));
        return getAllRanks();
    }

    public Iterable<DepartmentD> save(DepartmentD object) {
        departmentRepository.save(departmentMapper.toEntity(object));
        return getAllDepartments();
    }

    public Iterable<CountryD> getAllCountries() {
        return countryMapper.toDtoList(countryRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<RegionD> getAllRegions() {
        return regionMapper.toDtoList(regionRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<VehicleMarkD> getAllMarks() {
        return markMapper.toDtoList(markRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<VehicleModelD> getAllModels() {
        return modelMapper.toDtoList(modelRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<RankD> getAllRanks() {
        return rankMapper.toDtoList(rankRepository.findAll(Sort.by(TITLE_SORT)));
    }

    public Iterable<DepartmentD> getAllDepartments() {
        return departmentMapper.toDtoList(departmentRepository.findAll(Sort.by(TITLE_SORT)));
    }
}
