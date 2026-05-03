package com.example.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTest {
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to API Gateway!";
    }
}
