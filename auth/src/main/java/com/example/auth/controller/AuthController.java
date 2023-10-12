package com.example.auth.controller;

import com.example.auth.dtos.Dto;
import com.example.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("login")
    public Dto.TokenDTO login(@RequestBody Dto.AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("token/validate")
    public Dto.TokenDTO validateToken(@RequestHeader String accessToken) {
        return service.validateToken(accessToken);
    }

    @PostMapping("logout")
    public HashMap<String, Object> logout(@RequestHeader String accessToken) {
        service.logout(accessToken);
        var response = new HashMap<String, Object>();
        response.put("status", "OK");
        response.put("code", 200);
        return response;
    }

    @GetMapping("user")
    public Dto.AuthUserResponse getAuthenticatedUser(
            @RequestHeader String accessToken) {
        return service.getAuthenticatedUser(accessToken);
    }


}