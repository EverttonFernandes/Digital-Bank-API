package com.digitalbankapi.api.controller;

import com.digitalbankapi.application.service.ApplicationStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Map;

import static org.mockito.Mockito.mock;

class ApplicationStatusApiTest {

    private final ApplicationStatusService applicationStatusService = mock(ApplicationStatusService.class);
    private final ApplicationStatusApi applicationStatusApi = new ApplicationStatusApi(applicationStatusService);

    @Test
    @DisplayName("Deve retornar status da aplicacao vindo do service")
    void shouldReturnApplicationStatusFromService() {
        Map<String, String> expectedStatus = Map.of("application", "digital-bank-api", "status", "running");

        BDDMockito.given(applicationStatusService.getApplicationStatus()).willReturn(expectedStatus);

        Map<String, String> response = applicationStatusApi.getApplicationStatus();

        Assertions.assertEquals(expectedStatus, response);
    }
}
