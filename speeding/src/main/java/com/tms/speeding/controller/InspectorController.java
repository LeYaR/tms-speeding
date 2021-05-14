package com.tms.speeding.controller;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tms.speeding.dto.InspectorD;
import com.tms.speeding.entity.EmptyObject;
import com.tms.speeding.entity.Inspector;
import com.tms.speeding.mappers.InspectorMapper;
import com.tms.speeding.repository.InspectorRepository;
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
@RequestMapping(path="/inspectors")
public class InspectorController {

    @Autowired
    private InspectorRepository inspectorRepository;

    @Autowired
    private InspectorMapper mapService;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody InspectorD inspector) {
        try {
            return new ResponseEntity<>(inspectorRepository.save(mapService.toEntity(inspector)).getId(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(InspectorController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.SV_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get")
    @ResponseBody
    public ResponseEntity<Object> getAll() {
        try {
            return new ResponseEntity<>(mapService.toDtoList(inspectorRepository.findAll()), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(InspectorController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get", params = {"id"})
    @ResponseBody
    public ResponseEntity<Object> getById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        try {
            Optional<Inspector> inspector = inspectorRepository.findById(id);
            return new ResponseEntity<>(inspector.isPresent() ? mapService.toDto(inspector.get()) : new EmptyObject(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(InspectorController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/get", params = {"search"})
    @ResponseBody
	public ResponseEntity<Object> getByString(String search) {
        try {
            return new ResponseEntity<>(mapService.toDtoList(inspectorRepository.findByAll(search)), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(InspectorController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

    @PostMapping(path="/get", params = {"page"})
    @ResponseBody
	public ResponseEntity<Object> getByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        int start = (page - 1) * limit;
        try {
            return new ResponseEntity<>(mapService.toDtoList(inspectorRepository.findAll(PageRequest.of(start, limit)).getContent()), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(InspectorController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(SomeConstants.RD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
