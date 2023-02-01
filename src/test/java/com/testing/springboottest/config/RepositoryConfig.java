package com.testing.springboottest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.testing.springboottest" })
@EntityScan("com.testing.springboottest.entity")
@EnableJpaRepositories("com.testing.springboottest.repository")
public class RepositoryConfig {
}
