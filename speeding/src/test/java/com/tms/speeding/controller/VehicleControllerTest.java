package com.tms.speeding.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tms.speeding.domain.dbo.VehicleDbo;
import com.tms.speeding.domain.dto.VehicleDto;
import com.tms.speeding.domain.mapper.VehicleMapper;
import com.tms.speeding.service.VehicleService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {


    private final GsonBuilder GSON_BUILDER = new GsonBuilder().setPrettyPrinting();
    private final Gson PARSER = GSON_BUILDER.create();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private VehicleService service;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void incorrectAddressQueryTest() throws Exception {
        Integer id = 1;

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setRegNumber("number");
        vehicleDto.setVin("vin");
        vehicleDto.setId(id);


        Mockito.when(service.getById(0)).thenReturn(vehicleDto);

        var mvcResult = this.mockMvc.perform(post("/vehicles/anyString")
                .param("id", "1")
                .with((csrf())))
                .andExpect(status().is4xxClientError());

    }
}