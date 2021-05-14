package com.tms.speeding.controller;

import com.tms.speeding.dto.PersonD;
import com.tms.speeding.service.PersonService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/people")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<PersonD> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"id"})
    public PersonD getAllById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        return service.getById(id);
    }
    
    @GetMapping(params = {"search"})
	public Iterable<PersonD> getAllByString(String search) {
        return service.getAllByString(search);
	}

    @GetMapping(params = {"page"})
	public Iterable<PersonD> getAllByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return service.getAllByPage(page, limit);
	}

    @GetMapping(params = {"search", "page"})
	public Iterable<PersonD> getAllByPageAndString(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                                   String search) {
        return service.getAllByPageAndString(search, page, limit);
	}

    @PostMapping(path="/save")
    public PersonD save(@RequestBody PersonD person) {
        return service.save(person);
    }

}
