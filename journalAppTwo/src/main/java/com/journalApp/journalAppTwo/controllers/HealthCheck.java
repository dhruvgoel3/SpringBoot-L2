package com.journalApp.journalAppTwo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    public String HeathCheck() {
        return "Application Okk ha jii";
    }

    @GetMapping("/HealthCheck")
    public String healthCheck() {
        return "Application Okk ha jii";
    }
}
