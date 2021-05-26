package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.domain.dbo.PersonDbo;
import com.tms.speeding.domain.dto.PersonDto;

import com.tms.speeding.domain.mapper.PersonMapper;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseObject getAll() {
        return Auxiliary.prepareListResponse(repository.count(),
                                   mapper.toDtoList(repository.findAll()));
    }

    public PersonDto getById(Integer id) {
        final PersonDbo entity = repository.findById(id).orElse(null);
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

    public boolean validate(PersonDto object) {
        return !Auxiliary.isEmpty(object.getLastName()) &&
               !Auxiliary.isEmpty(object.getFirstName()) &&
               object.getBornDate() != null;
    }

    public ResponseObject save(PersonDto object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<PersonDbo> entity = repository.findByAll(object);
        if (entity.isPresent()) {
            final int id = entity.get().getId();
            if (object.getId() != null && object.getId() != id) {
                return new ResponseObject("warning", Auxiliary.SV_EXISTS);
            }
            object.setId(id);
            var person = entity.get();
            person.setLastName(object.getLastName());
            person.setFirstName(object.getFirstName());
            person.setMiddleName(object.getMiddleName());
            person.setBornDate(object.getBornDate());
            person.setPersonalNumber(object.getPersonalNumber());
            repository.save(person);
            return new ResponseObject("warning", Auxiliary.SV_OVERWRITTEN);
        }
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }
}
