package com.digitalbankapi.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ApplicationStatusServiceTest {

    private final ApplicationStatusService applicationStatusService = new ApplicationStatusService();

    @Test
    @DisplayName("Deve retornar o status simples da aplicacao")
    void shouldReturnSimpleApplicationStatus() {
        // GIVEN

        // WHEN
        Map<String, String> applicationStatus = applicationStatusService.getApplicationStatus();

        // THEN
        Assertions.assertEquals("digital-bank-api", applicationStatus.get("application"));
        Assertions.assertEquals("running", applicationStatus.get("status"));
    }
}
