package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.service.ApplicationStatusService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class ApplicationStatusController {

    private final ApplicationStatusService applicationStatusService;

    public ApplicationStatusController(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @GetMapping
    public Map<String, String> getApplicationStatus() {
        return applicationStatusService.getApplicationStatus();
    }
}
