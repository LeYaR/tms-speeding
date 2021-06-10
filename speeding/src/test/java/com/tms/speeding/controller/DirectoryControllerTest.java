package com.tms.speeding.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tms.speeding.domain.dto.*;
import com.tms.speeding.service.DirectoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DirectoryController.class)
class DirectoryControllerTest {

    private final GsonBuilder GSON_BUILDER = new GsonBuilder().setPrettyPrinting();
    private final Gson PARSER = GSON_BUILDER.create();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private DirectoryService service;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void verifyOnlyOneProcessingOfGettingDirectories() throws Exception {

        Mockito.when(service.getDirectories()).thenReturn(new DirectoryDto());

        mockMvc.perform(post("/dirs").with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(service, times(1)).getDirectories();
    }

    @Test
    public void assertTrueIfCountrySavingIsSuccessful() throws Exception {

        var savedCountry = new CountryDto();
        savedCountry.setTitle("Country");
        savedCountry.setIso("iso");

        var gettingCountry = new CountryDto();
        gettingCountry.setId(1);
        gettingCountry.setTitle("Country");
        gettingCountry.setIso("iso");

        List<CountryDto> countryList = new ArrayList<>();
        countryList.add(gettingCountry);

        Mockito.when(service.save(any(CountryDto.class))).thenReturn(countryList);

        var returnedJsonCountry = mockMvc.perform(post("/dirs/countries/save")
                .contentType("application/json")
                .content(PARSER.toJson(savedCountry))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<CountryDto>>() {
        }.getType();

        List<CountryDto> returnedCountryFromServer = PARSER.fromJson(returnedJsonCountry, listType);

        boolean isSuccessfulSave = (returnedCountryFromServer.get(0).getIso().equals(savedCountry.getIso()) &&
                returnedCountryFromServer.get(0).getTitle().equals(savedCountry.getTitle()) &&
                returnedCountryFromServer.get(0).getId().equals(1));

        assertTrue(isSuccessfulSave);
    }

    @Test
    public void assertTrueIfGetAllCountriesOperationIsSuccessful() throws Exception {
        var country = new CountryDto();
        country.setTitle("Country");

        List<CountryDto> countryList = new ArrayList<>();
        countryList.add(country);

        Mockito.when(service.getAllCountries()).thenReturn(countryList);

        String jsonResult = mockMvc.perform(post("/dirs/countries")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<CountryDto>>() {
        }.getType();

        List<CountryDto> returnedCountriesFromServer = PARSER.fromJson(jsonResult, listType);

        boolean isSuccessfulOperation =
                (Objects.equals(returnedCountriesFromServer.get(0).getTitle(), country.getTitle()));

        assertTrue(isSuccessfulOperation);
    }

    @Test
    public void assertTrueIfSavingRegionIsSuccessful() throws Exception {
        var savedRegion = new RegionDto();
        savedRegion.setTitle("Region");

        var gettingRegion = new RegionDto();
        gettingRegion.setId(1);
        gettingRegion.setTitle("Region");

        List<RegionDto> regionList = new ArrayList<>();
        regionList.add(gettingRegion);

        Mockito.when(service.save(any(RegionDto.class))).thenReturn(regionList);


        var returnedJsonRegion = mockMvc.perform(post("/dirs/regions/save")
                .contentType("application/json")
                .content(PARSER.toJson(savedRegion))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<CountryDto>>() {
        }.getType();

        List<CountryDto> returnedRegionFromServer = PARSER.fromJson(returnedJsonRegion, listType);

        boolean isSuccessfulSave = (returnedRegionFromServer.get(0).getTitle().equals(savedRegion.getTitle()) &&
                returnedRegionFromServer.get(0).getId().equals(1));

        assertTrue(isSuccessfulSave);
    }

    @Test
    public void passedIfGettingRegionsIsSuccessful() throws Exception {
        var region = new RegionDto();
        region.setTitle("Country");

        List<RegionDto> regionList = new ArrayList<>();
        regionList.add(region);

        Mockito.when(service.getAllRegions()).thenReturn(regionList);

        String jsonResult = mockMvc.perform(post("/dirs/regions")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<CountryDto>>() {
        }.getType();

        List<CountryDto> returnedRegionsFromServer = PARSER.fromJson(jsonResult, listType);

        boolean isSuccessfulOperation =
                (Objects.equals(returnedRegionsFromServer.get(0).getTitle(), region.getTitle()));

        assertTrue(isSuccessfulOperation);
    }

    @Test
    public void passedIfReturnedContentIsEqualToEmptyStringAndServiceInvokedOnlyOnce() throws Exception {
        var savedMark = new VehicleMarkDto();
        savedMark.setTitle("testMark");

        Mockito.when(service.save(any(VehicleMarkDto.class))).thenReturn(null);

        mockMvc.perform(post("/dirs/marks/save")
                .contentType("application/json")
                .content(PARSER.toJson(savedMark))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(service, times(1)).save(any(VehicleMarkDto.class));
    }

    @Test
    public void passedIfGettingDepartmentsIsSuccessful() throws Exception {
        var department = new DepartmentDto();
        department.setTitle("testDep");

        List<DepartmentDto> departmentList = new ArrayList<>();
        departmentList.add(department);

        Mockito.when(service.getAllDepartments()).thenReturn(departmentList);

        String jsonResult = mockMvc.perform(post("/dirs/departments")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<DepartmentDto>>() {
        }.getType();

        List<DepartmentDto> returnedDepartmentsFromServer = PARSER.fromJson(jsonResult, listType);

        boolean isSuccessfulOperation =
                (Objects.equals(returnedDepartmentsFromServer.get(0).getTitle(), department.getTitle()));

        assertTrue(isSuccessfulOperation);
    }

    @Test
    public void saveRank() throws Exception {

        var savedRank = new RankDto();
        savedRank.setTitle("Mayor");

        var gettingRank = new RankDto();
        gettingRank.setId(1);
        gettingRank.setTitle("Mayor");

        List<RankDto> regionList = new ArrayList<>();
        regionList.add(gettingRank);

        Mockito.when(service.save(any(RankDto.class))).thenReturn(regionList);


        var returnedJsonRegion = mockMvc.perform(post("/dirs/ranks/save")
                .contentType("application/json")
                .content(PARSER.toJson(savedRank))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<RankDto>>() {
        }.getType();

        List<RankDto> returnedRanksFromServer = PARSER.fromJson(returnedJsonRegion, listType);

        boolean isSuccessfulSave = (returnedRanksFromServer.get(0).getTitle().equals(savedRank.getTitle()) &&
                returnedRanksFromServer.get(0).getId().equals(1));

        assertTrue(isSuccessfulSave);
    }

    @Test
    public void test() throws Exception {
        var model = new VehicleModelDto();
        model.setTitle("testModel");

        List<VehicleModelDto> departmentList = new ArrayList<>();
        departmentList.add(model);

        Mockito.when(service.getAllModels()).thenReturn(departmentList);

        String jsonResult = mockMvc.perform(post("/dirs/models")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Type listType = new TypeToken<List<VehicleModelDto>>() {
        }.getType();

        List<VehicleModelDto> returnedModelsFromServer = PARSER.fromJson(jsonResult, listType);

        boolean isSuccessfulOperation =
                (Objects.equals(returnedModelsFromServer.get(0).getTitle(), model.getTitle()));

        assertTrue(isSuccessfulOperation);
    }
}
