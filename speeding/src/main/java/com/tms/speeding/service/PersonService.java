package com.tms.speeding.service;

import java.util.Date;
import java.util.Optional;

import com.tms.speeding.dto.PersonD;
import com.tms.speeding.entity.Person;
import com.tms.speeding.mappers.PersonMapper;
import com.tms.speeding.repository.PersonRepository;

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

    public PersonD save(PersonD entity) {
        String lastName = entity.getLastName();
        String firstName = entity.getFirstName();
        Date bornDate = entity.getBornDate();
        String personlNumber = entity.getPersonalNumber();
        Optional<Person> person = repository.findByAll(lastName, firstName, bornDate, personlNumber);
        if (person.isPresent()) {
            return mapper.toDto(person.get());
        }
        return mapper.toDto(repository.save(mapper.toEntity(entity)));
    }
}
