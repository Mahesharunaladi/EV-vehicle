package com.ev.prediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot application for EV Demand Prediction
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ev")
public class EVDemandPredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EVDemandPredictionApplication.class, args);
    }
}
