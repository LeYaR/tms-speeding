package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.dto.InspectorD;
import com.tms.speeding.entity.Inspector;
import com.tms.speeding.mappers.InspectorMapper;
import com.tms.speeding.repository.InspectorRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InspectorService {

    private final InspectorRepository repository;
    private final PersonRepository pRepository;
    private final InspectorMapper mapper;

    public InspectorService(InspectorRepository repository, PersonRepository pRepository, InspectorMapper mapper) {
        this.repository = repository;
        this.pRepository = pRepository;
        this.mapper = mapper;
    }

    public Iterable<InspectorD> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public InspectorD getById(Integer id) {
        final Inspector entity = repository.findById(id).orElse(null);
        return entity == null ? null : mapper.toDto(entity);
    }

    public Iterable<InspectorD> getAllByString(String search) {
        return mapper.toDtoList(repository.findByAll(search));
    }

    public Iterable<InspectorD> getAllByPage(Integer page, Integer limit) {
        return mapper.toDtoList(repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    public Iterable<InspectorD> getAllByPageAndString(String search, Integer page, Integer limit) {
        return mapper.toDtoList(repository.findByAll(search, PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    private boolean validate(InspectorD object) {
        return !Auxiliary.isEmpty(object.getBadgeNumber()) &&
               object.getPerson() != null && object.getPerson().getId() != null &&
               pRepository.findById(object.getPerson().getId()).orElse(null) != null;
    }

    public ResponseObject save(InspectorD object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<Inspector> entity = repository.findByAll(object);
        if (entity.isPresent()) {
            final int id = entity.get().getId();
            if (object.getId() == null || object.getId() != id) {
                return new ResponseObject("warning", Auxiliary.SV_EXISTS);
            }
        }
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }
}
