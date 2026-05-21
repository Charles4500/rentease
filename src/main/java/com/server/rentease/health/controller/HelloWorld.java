package com.server.rentease.health.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class HelloWorld {

    @GetMapping("/hello")
    public String hello(){
     return "Hello World";
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "sub",   jwt.getSubject(),
                "email", jwt.getClaimAsString("email"),
                "name",  jwt.getClaimAsString("name")
        );
    }


}
