package com.tms.speeding.controller;

import com.tms.speeding.dto.DirectoryD;
import com.tms.speeding.service.DirectoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/dirs")
public class DirectoryController {

    private final DirectoryService service;

    public DirectoryController(DirectoryService service) {
        this.service = service;
    }

    @PostMapping
    public DirectoryD getAll() {
        return service.getDirectories();
    }
}
