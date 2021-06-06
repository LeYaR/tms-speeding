package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.domain.dbo.InspectorDbo;
import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.InspectorDto;

import com.tms.speeding.domain.mapper.InspectorMapper;
import com.tms.speeding.domain.mapper.PersonMapper;
import com.tms.speeding.repository.DepartmentRepository;
import com.tms.speeding.repository.InspectorRepository;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.RankRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InspectorService {

    private final InspectorRepository repository;
    private final PersonRepository pRepository;
    private final RankRepository rRepository;
    private final DepartmentRepository dRepository;
    private final InspectorMapper mapper;
    private final PersonMapper pMapper;

    public InspectorService(InspectorRepository repository,
                            PersonRepository pRepository,
                            InspectorMapper mapper,
                            RankRepository rRepository,
                            DepartmentRepository dRepository,
                            PersonMapper pMapper) {
        this.repository = repository;
        this.pRepository = pRepository;
        this.rRepository = rRepository;
        this.dRepository = dRepository;
        this.mapper = mapper;
        this.pMapper = pMapper;
    }

    public ResponseObject getAll() {
        return Auxiliary.prepareListResponse(repository.count(),
                mapper.toDtoList(repository.findAll()));
    }

    public InspectorDto getById(Integer id) {
        final InspectorDbo entity = repository.findById(id).orElse(null);
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

    private boolean validate(InspectorDto object) {
        return !(object.getBadgeNumber().isEmpty()) &&
                object.getPerson() != null &&
                (object.getRank() == null || rRepository.findById(object.getRank()).isPresent()) &&
                (object.getDepartment() == null || rRepository.findById(object.getDepartment()).isPresent());
    }

    public ResponseObject save(InspectorDto object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<PersonDbo> personE = pRepository.findByAll(object.getPerson());
        var person = personE.isPresent() ? personE.get() : pRepository.save(pMapper.toEntity(object.getPerson()));
        Optional<InspectorDbo> entity = repository.findByAll(object);
        if (entity.isPresent()) {
            final int id = entity.get().getId();
            if (object.getId() == null || object.getId() != id) {
                return new ResponseObject("warning", Auxiliary.SV_EXISTS);
            }
            var inspector = entity.get();
            inspector.setPerson(person);
            inspector.setBadgeNumber(object.getBadgeNumber());
            inspector.setRank(object.getRank() != null ? rRepository.findById(object.getRank()).orElse(null) : null);
            inspector.setDepartment(object.getDepartment() != null ? dRepository.findById(object.getDepartment()).orElse(null) : null);
            repository.save(inspector);
            return new ResponseObject();
        }
        object.getPerson().setId(person.getId());
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }
}
