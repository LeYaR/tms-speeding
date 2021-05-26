package com.tms.speeding.controller;

import com.tms.speeding.domain.dto.ViolationDto;
import com.tms.speeding.domain.dto.ViolationFilter;
import com.tms.speeding.service.ViolationService;
import com.tms.speeding.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/violations")
public class ViolationController {
    
    private final ViolationService service;

    public ViolationController(ViolationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseObject getAll() {
        return service.getAll();
    }

    @PostMapping(path = "generate/", params = {"limit"})
	public ResponseObject generate(@RequestParam(value = "limit", defaultValue = "0") Integer limit,
                                   @RequestParam(value = "start", defaultValue = "0") String start,
                                   @RequestParam(value = "end", defaultValue = "0") String end) {
        return service.generate(limit, start, end);
	}

    @PostMapping(path = "generate/")
	public ResponseObject generate() {
        return service.generate();
	}

    @PostMapping(path = "filter/")
	public ResponseObject getFiltered(@RequestBody ViolationFilter filter) {
        return service.getFiltered(filter);
	}

    @PostMapping(params = {"id"})
    public ViolationDto getAllById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
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
    public ResponseObject save(@RequestBody ViolationDto object) {
        return service.save(object);
    }
}
