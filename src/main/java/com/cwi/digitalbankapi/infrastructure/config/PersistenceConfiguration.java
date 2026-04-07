package com.cwi.digitalbankapi.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.cwi.digitalbankapi.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "com.cwi.digitalbankapi.infrastructure.persistence.repository")
public class PersistenceConfiguration {
}
