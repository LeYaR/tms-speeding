package com.tms.speeding.service;

import com.tms.speeding.dto.ViolationD;
import com.tms.speeding.entity.Violation;
import com.tms.speeding.mappers.ViolationMapper;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.VehicleRepository;
import com.tms.speeding.repository.ViolationRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class ViolationService {
    private final ViolationRepository repository;
    private final PersonRepository pRepository;
    private final VehicleRepository vRepository;
    private final ViolationMapper mapper;

    public ViolationService(ViolationRepository repository,
                            PersonRepository pRepository,
                            VehicleRepository vRepository,
                            ViolationMapper mapper) {
        this.repository = repository;
        this.pRepository = pRepository;
        this.vRepository = vRepository;
        this.mapper = mapper;
    }

    public Iterable<ViolationD> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public ViolationD getById(Integer id) {
        final Violation entity = repository.findById(id).orElse(null);
        return entity == null ? null : mapper.toDto(entity);
    }

    public Iterable<ViolationD> getAllByString(String search) {
        return mapper.toDtoList(repository.findByAll(search));
    }

    public Iterable<ViolationD> getAllByPage(Integer page, Integer limit) {
        return mapper.toDtoList(repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    public Iterable<ViolationD> getAllByPageAndString(String search, Integer page, Integer limit) {
        return mapper.toDtoList(repository.findByAll(search, PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    private boolean validate(ViolationD object) {
        return object.getActualSpeed() != null &&
               object.getSpeedLimit() != null &&
               object.getGuilty() != null && object.getGuilty().getId() != null &&
               object.getInspector() != null && object.getInspector().getId() != null &&
               object.getVehicle() != null && object.getVehicle().getId() != null &&
               pRepository.findById(object.getGuilty().getId()).orElse(null) != null &&
               pRepository.findById(object.getInspector().getId()).orElse(null) != null &&
               vRepository.findById(object.getVehicle().getId()).orElse(null) != null;
    }

    public ResponseObject save(ViolationD object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }
}