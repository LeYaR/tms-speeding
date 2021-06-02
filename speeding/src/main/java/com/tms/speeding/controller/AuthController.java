package com.tms.speeding.controller;

import com.tms.speeding.service.LoginService;
import com.tms.speeding.util.ResponseObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login/")
public class AuthController {
    private final LoginService service;

    public AuthController(LoginService service) {
        this.service = service;
    }

    @PostMapping(path = {"/login"})
    public ResponseObject logIn(@RequestParam(value = "login") String login,
                                @RequestParam(value = "password") String password) {
        return service.logIn(login, password);
    }

    @PostMapping(path = {"/registration"})
    public ResponseObject regIn(@RequestParam(value = "login") String login,
                                @RequestParam(value = "password") String password) {
        return service.regIn(login, password);
    }

}






