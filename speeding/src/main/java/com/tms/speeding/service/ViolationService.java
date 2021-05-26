package com.tms.speeding.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tms.speeding.domain.dbo.ViolationDbo;
import com.tms.speeding.domain.dto.ViolationDto;
import com.tms.speeding.domain.dto.ViolationFilter;

import com.tms.speeding.domain.mapper.ViolationMapper;
import com.tms.speeding.repository.PersonRepository;
import com.tms.speeding.repository.VehicleRepository;
import com.tms.speeding.repository.ViolationRepository;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.exception.CustomException;
import com.tms.speeding.util.ResponseObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ViolationService {
    private final ViolationRepository repository;
    private final PersonRepository pRepository;
    private final VehicleRepository vRepository;
    private final ViolationMapper mapper;

    public ViolationService(ViolationRepository repository,
                            PersonRepository pRepository,
                            VehicleRepository vRepository,
                            ViolationMapper mapper) {
        this.repository = repository;
        this.pRepository = pRepository;
        this.vRepository = vRepository;
        this.mapper = mapper;
    }

    public ResponseObject getAll() {
        return Auxiliary.prepareListResponse(repository.count(),
                                   mapper.toDtoList(repository.findAll()));
    }

    public ViolationDto getById(Integer id) {
        final ViolationDbo entity = repository.findById(id).orElse(null);
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

    private boolean validate(ViolationDto object) {
        return object.getActualSpeed() != null &&
               object.getSpeedLimit() != null &&
               object.getGuilty() != null && object.getGuilty().getId() != null &&
               object.getInspector() != null && object.getInspector().getId() != null &&
               object.getVehicle() != null && object.getVehicle().getId() != null &&
               pRepository.findById(object.getGuilty().getId()).isPresent() &&
               pRepository.findById(object.getInspector().getId()).isPresent() &&
               vRepository.findById(object.getVehicle().getId()).isPresent();
    }

    public ResponseObject save(ViolationDto object) {
        if (!validate(object)) {
            return new ResponseObject(false, "error", Auxiliary.SV_INVALID);
        }
        repository.save(mapper.toEntity(object));
        return new ResponseObject();
    }

    private Specification<ViolationDbo> byGuilty (Integer guilty) {
        return (root, query, builder) -> guilty == null ? builder.conjunction() : builder.equal(root.get("guilty").get("id"), guilty);
    }

    private Specification<ViolationDbo> byVehicle (Integer vehicle) {
        return (root, query, builder) -> vehicle == null ? builder.conjunction() : builder.equal(root.get("vehicle").get("id"), vehicle);
    }

    private Specification<ViolationDbo> byInspector (Integer inspector) {
        return (root, query, builder) -> inspector == null ? builder.conjunction() : builder.equal(root.get("inspector").get("person").get("id"), inspector);
    }

    private Specification<ViolationDbo> byRegion (Integer region) {
        return (root, query, builder) -> region == null ? builder.conjunction() : builder.equal(root.get("region").get("id"), region);
    }

    private Specification<ViolationDbo> byDate (Date date) {
        return (root, query, builder) -> date == null ? builder.conjunction() : builder.equal(root.get("violationDate"), date);
    }

    public ResponseObject getFiltered(ViolationFilter filter) {
        final Integer page = filter.getPage() == null ? 1 : filter.getPage();
        final Integer limit = filter.getLimit() == null ? 20 : filter.getLimit();

        Specification<ViolationDbo> spec = Specification.where(byGuilty(filter.getGuilty()))
                                                     .and(byInspector(filter.getInspector()))
                                                     .and(byVehicle(filter.getVehicle()))
                                                     .and(byRegion(filter.getRegion()))
                                                     .and(byDate(filter.getDate()));
        return Auxiliary.prepareListResponse(repository.count(spec),
                                   mapper.toDtoList(repository.findAll(spec, PageRequest.of(Math.max(page - 1, 0), limit)).getContent()));
    }

    public ResponseObject generate(Integer limit, String start, String end) {
        var format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            repository.generateViolations(limit, format.parse(start), format.parse(end));
            return new ResponseObject();
        } catch (ParseException ex) {
            throw new CustomException("unable to parse the date passed");
        }
    }

    public ResponseObject generate() {
        var format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            repository.generateViolations(30, format.parse("01-01-2000"), format.parse("01-01-2020"));
            return new ResponseObject();
        } catch (ParseException ex) {
            throw new CustomException("Unable to parse the date");
        }
    }
}
