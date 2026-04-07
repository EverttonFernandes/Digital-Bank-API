package com.cwi.digitalbankapi.application.service;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusService {

    public Map<String, String> getApplicationStatus() {
        return Map.of(
            "application", "digital-bank-api",
            "status", "running"
        );
    }
}
