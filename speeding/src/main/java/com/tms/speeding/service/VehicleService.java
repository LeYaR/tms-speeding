package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.dto.VehicleD;
import com.tms.speeding.entity.Vehicle;
import com.tms.speeding.mapper.VehicleMapper;
import com.tms.speeding.repository.RegionRepository;
import com.tms.speeding.repository.VehicleModelRepository;
import com.tms.speeding.repository.VehicleRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository repository;
    private final VehicleModelRepository mRepository;
    private final RegionRepository rRepository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository repository,
                          VehicleMapper mapper,
                          VehicleModelRepository mRepository,
                          RegionRepository rRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.mRepository = mRepository;
        this.rRepository = rRepository;
    }

    public ResponseObject getAll() {
        return Auxiliary.prepareListResponse(repository.count(),
                                   mapper.toDtoList(repository.findAll()));
    }

    public VehicleD getById(Integer id) {
        final Vehicle entity = repository.findById(id).orElse(null);
        return entity == null ? null : mapper.toDto(entity);
    }

    public ResponseObject getAllByString(String search) {
        return Auxiliary.prepareListResponse(repository.countBySearch(search),
                                   mapper.toDtoList(repository.findByAll(search)));
    }

    public ResponseObject getAllByPage(Integer page, Integer limit) {
        return Auxiliary.prepareListResponse(repository.count(),
                                   mapper.toDtoList(repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit)).getContent()));
    }

    public ResponseObject getAllByPageAndString(String search, Integer page, Integer limit) {
        return Auxiliary.prepareListResponse(repository.countBySearch(search),
                                   mapper.toDtoList(repository.findByAll(search, PageRequest.of(Math.max(page - 1, 0), limit)).getContent()));
    }

    public boolean validate(VehicleD object) {
        return !Auxiliary.isEmpty(object.getVin()) &&
               !Auxiliary.isEmpty(object.getRegNumber()) &&
               object.getModel() != null;
    }

    public ResponseObject save(VehicleD object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<Vehicle> entity = repository.findByAll(object);
        if (entity.isPresent()) {
            final int id = entity.get().getId();
            if (object.getId() != null && object.getId() != id) {
                return new ResponseObject("warning", Auxiliary.SV_EXISTS);
            }
            object.setId(id);
            var entry = entity.get();
            entry.setVin(object.getVin());
            entry.setRegNumber(object.getRegNumber());
            entry.setModel(mRepository.findById(object.getModel()).orElse(null));
            entry.setRegion(object.getRegion() == null ? null : rRepository.findById(object.getRegion()).orElse(null));
            repository.save(entry);
            return object.getId() != null ? new ResponseObject() : new ResponseObject("warning", Auxiliary.SV_OVERWRITTEN);
        }
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }

}
