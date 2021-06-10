package com.tms.speeding.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tms.speeding.domain.dto.InspectorDto;
import com.tms.speeding.domain.dto.PersonDto;
import com.tms.speeding.domain.dto.VehicleDto;
import com.tms.speeding.domain.dto.ViolationDto;
import com.tms.speeding.repository.ViolationRepository;
import com.tms.speeding.service.ViolationService;
import com.tms.speeding.util.Auxiliary;
import com.tms.speeding.util.ResponseObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViolationController.class)
class ViolationControllerTest {

    private final GsonBuilder GSON_BUILDER = new GsonBuilder().setPrettyPrinting();
    private final Gson PARSER = GSON_BUILDER.create();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ViolationService service;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void assertTrueIfGettingViolationFromServerIsSuccessful() throws Exception {

        ViolationDto violation = new ViolationDto();

        violation.setViolationDate(new Date());
        violation.setActualSpeed(120);
        violation.setNote("ghost rider");
        violation.setSpeedLimit(40);
        violation.setRepaid(true);
        violation.setGuilty(new PersonDto());
        violation.setVehicle(new VehicleDto());
        violation.setInspector(new InspectorDto());
        violation.setId(1);

        List<ViolationDto> violationList = new ArrayList<>();
        violationList.add(violation);

        ResponseObject responseObject = Auxiliary.prepareListResponse(1, violationList);

        Mockito.when(service.getAll()).thenReturn(responseObject);

        var returnedJsonCountry = mockMvc.perform(post("/violations")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ResponseObject responseFromServer = PARSER.fromJson(returnedJsonCountry, ResponseObject.class);

        assertTrue(responseFromServer.isSuccess());
    }
}
