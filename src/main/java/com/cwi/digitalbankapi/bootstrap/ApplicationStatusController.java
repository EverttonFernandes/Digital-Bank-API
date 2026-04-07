package com.cwi.digitalbankapi.bootstrap;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class ApplicationStatusController {

    @GetMapping
    public Map<String, String> getApplicationStatus() {
        return Map.of(
            "application", "digital-bank-api",
            "status", "running"
        );
    }
}
