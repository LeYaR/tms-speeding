package com.tms.speeding.controllers;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tms.speeding.dto.ViolationDTO;
import com.tms.speeding.entities.EmptyObject;
import com.tms.speeding.entities.Violation;
import com.tms.speeding.mappers.ViolationMapper;
import com.tms.speeding.repos.ViolationRepository;
import com.tms.speeding.util.SomeConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/violations")
public class ViolationController {
    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private ViolationMapper mapService;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ViolationDTO person) {
        try {
            return new ResponseEntity<>(violationRepository.save(mapService.toEntity(person)).getId(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.SV_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get")
    @ResponseBody
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<>(mapService.getDtoList(violationRepository.findAll()), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get", params = {"id"})
    @ResponseBody
    public ResponseEntity<Object> getById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        try {
            Optional<Violation> person = violationRepository.findById(id);
            return new ResponseEntity<>(person.isPresent() ? mapService.toDto(person.get()) : new EmptyObject(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get", params = {"page"})
    @ResponseBody
	public ResponseEntity<Object> getByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        int start = (page - 1) * limit;
        try {
            return new ResponseEntity<>(mapService.getDtoList(violationRepository.findAll(PageRequest.of(start, limit)).getContent()), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
