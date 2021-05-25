package com.tms.speeding.controller;

import com.tms.speeding.entity.Login;
import com.tms.speeding.service.LoginService;
import com.tms.speeding.util.ResponseObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login/process")
public class AuthController {
    private final LoginService service;

    public AuthController(LoginService service) {
        this.service = service;
    }

    @PostMapping(params = {"old"})
    public ResponseObject logIn(@RequestParam(value = "old") String login,
                                @RequestParam(value = "password") String password) {
        return service.logIn(login, password);
    }

    @PostMapping(params = {"new"})
    public ResponseObject regIn(@RequestParam(value = "new") String login,
                                @RequestParam(value = "password") String password) {
        return service.regIn(login, password);
    }

}






