package com.digitalbankapi.infrastructure.config;

import com.digitalbankapi.DigitalBankApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class ConfigurationSmokeTest {

    @Test
    @DisplayName("Deve instanciar configuracoes de infraestrutura")
    void shouldInstantiateInfrastructureConfigurations() {
        Assertions.assertNotNull(new DigitalBankApiApplication());
        Assertions.assertNotNull(new OpenApiConfiguration());
        Assertions.assertNotNull(new PersistenceConfiguration());
    }

    @Test
    @DisplayName("Deve iniciar aplicacao chamando SpringApplication")
    void shouldStartApplicationCallingSpringApplication() {
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            DigitalBankApiApplication.main(new String[]{"arg-1"});

            springApplicationMockedStatic.verify(() -> SpringApplication.run(DigitalBankApiApplication.class, new String[]{"arg-1"}));
        }
    }
}
