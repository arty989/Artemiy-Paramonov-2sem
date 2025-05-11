package com.example.S2_H1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Profile("!test & !no-db-test")
public class SchedulerConfig {}