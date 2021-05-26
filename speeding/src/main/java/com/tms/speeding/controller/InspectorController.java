package com.tms.speeding.controller;

import com.tms.speeding.domain.dto.InspectorDto;
import com.tms.speeding.service.InspectorService;
import com.tms.speeding.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/inspectors")
public class InspectorController {

    private final InspectorService service;

    public InspectorController(InspectorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseObject getAll() {
        return service.getAll();
    }

    @PostMapping(params = {"id"})
    public InspectorDto getAllById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        return service.getById(id);
    }
    
    @PostMapping(params = {"search"})
	public ResponseObject getAllByString(String search) {
        return service.getAllByString(search);
	}

    @PostMapping(params = {"page"})
	public ResponseObject getAllByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return service.getAllByPage(page, limit);
	}

    @PostMapping(params = {"search", "page"})
	public ResponseObject getAllByPageAndString(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                                String search) {
        return service.getAllByPageAndString(search, page, limit);
	}

    @PostMapping(path="/save")
    public ResponseObject save(@RequestBody InspectorDto object) {
        return service.save(object);
    }
}
