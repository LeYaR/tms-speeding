package com.tms.speeding.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tms.speeding.service.LoginService;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    private final GsonBuilder GSON_BUILDER = new GsonBuilder().setPrettyPrinting();
    private final Gson PARSER = GSON_BUILDER.create();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LoginService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void assertTrueIfLogInIsSuccessful() throws Exception {
        String login = "login";
        String password = "password";

        Mockito.when(service.logIn(login, password)).thenReturn(new ResponseObject());
        String url = "/login/login";

        var mvcResult = this.mockMvc.perform(post(url)
                .param("login", login)
                .param("password", password)
                .with((csrf())))
                .andExpect(status().isOk());

        var jsonAsString = mvcResult.andReturn().getResponse().getContentAsString();
        var responseObject = PARSER.fromJson(jsonAsString, ResponseObject.class);

        assertTrue(responseObject.isSuccess());
    }

    @Test
    public void assertFalseIfLogInIsNotSuccessful() throws Exception {
        String login = "login";
        String password = "incorrectPassword";

        Mockito.when(service.logIn(login, password)).thenReturn(
                new ResponseObject(false, "error", "incorrect password"));

        String url = "/login/login";

        var mvcResult = this.mockMvc.perform(post(url)
                .param("login", login)
                .param("password", password)
                .with((csrf())))
                .andExpect(status().isOk());

        var jsonAsString = mvcResult.andReturn().getResponse().getContentAsString();
        var responseObject = PARSER.fromJson(jsonAsString, ResponseObject.class);

        assertFalse(responseObject.isSuccess());
    }

    @Test
    public void assertTrueIfRegInIsSuccessful() throws Exception {
        String login = "login";
        String password = "password";

        Mockito.when(service.regIn(login, password)).thenReturn(new ResponseObject());
        String url = "/login/registration";

        var mvcResult = this.mockMvc.perform(post(url)
                .param("login", login)
                .param("password", password)
                .with((csrf())))
                .andExpect(status().isOk());

        var jsonAsString = mvcResult.andReturn().getResponse().getContentAsString();
        var responseObject = PARSER.fromJson(jsonAsString, ResponseObject.class);

        assertTrue(responseObject.isSuccess());
    }
}
