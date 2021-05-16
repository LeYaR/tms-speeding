package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.dto.InspectorDto;
import com.tms.speeding.dbo.InspectorDbo;
import com.tms.speeding.mapper.InspectorMapper;
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

    public Iterable<InspectorDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public InspectorDto getById(Integer id) {
        final InspectorDbo entity = repository.findById(id).orElse(null);
        return entity == null ? null : mapper.toDto(entity);
    }

    public Iterable<InspectorDto> getAllByString(String search) {
        return mapper.toDtoList(repository.findByAll(search));
    }

    public Iterable<InspectorDto> getAllByPage(Integer page, Integer limit) {
        return mapper.toDtoList(repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    public Iterable<InspectorDto> getAllByPageAndString(String search, Integer page, Integer limit) {
        return mapper.toDtoList(repository.findByAll(search, PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    private boolean validate(InspectorDto object) {
        return !Auxiliary.isEmpty(object.getBadgeNumber()) &&
               object.getPerson() != null && object.getPerson().getId() != null &&
               pRepository.findById(object.getPerson().getId()).orElse(null) != null;
    }

    public ResponseObject save(InspectorDto object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<InspectorDbo> entity = repository.findByAll(object);
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
