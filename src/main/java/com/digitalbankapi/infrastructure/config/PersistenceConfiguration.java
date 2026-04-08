package com.digitalbankapi.infrastructure.config;

import com.digitalbankapi.DigitalBankApiApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = DigitalBankApiApplication.class)
@EnableJpaRepositories(basePackages = "com.digitalbankapi.infrastructure.persistence.repository")
public class PersistenceConfiguration {
}
