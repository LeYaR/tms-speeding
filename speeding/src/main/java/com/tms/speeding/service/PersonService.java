package com.tms.speeding.service;

import java.util.Optional;

import com.tms.speeding.dto.PersonD;
import com.tms.speeding.entity.Person;
import com.tms.speeding.mapper.PersonMapper;
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

    public Iterable<PersonD> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public PersonD getById(Integer id) {
        final Person entity = repository.findById(id).orElse(null);
        return entity == null ? null : mapper.toDto(entity);
    }

    public Iterable<PersonD> getAllByString(String search) {
        return mapper.toDtoList(repository.findByAll(search));
    }

    public Iterable<PersonD> getAllByPage(Integer page, Integer limit) {
        return mapper.toDtoList(repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    public Iterable<PersonD> getAllByPageAndString(String search, Integer page, Integer limit) {
        return mapper.toDtoList(repository.findByAll(search, PageRequest.of(Math.max(page - 1, 0), limit)).getContent());
    }

    private boolean validate(PersonD object) {
        return !Auxiliary.isEmpty(object.getLastName()) &&
               !Auxiliary.isEmpty(object.getFirstName()) &&
               object.getBornDate() != null;
    }

    public ResponseObject save(PersonD object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        Optional<Person> entity = repository.findByAll(object);
        ResponseObject result = new ResponseObject();
        if (entity.isPresent()) {
            final int id = entity.get().getId();
            if (object.getId() != null && object.getId() != id) {
                return new ResponseObject("warning", Auxiliary.SV_EXISTS);
            } else if (object.getId() == null) {
                result = new ResponseObject("warning", Auxiliary.SV_OVERWRITTEN);
            }
            object.setId(id);
        }
        repository.save(mapper.toEntity(object));
        return result;
    }
}
