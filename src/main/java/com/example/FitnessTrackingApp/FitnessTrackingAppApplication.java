package com.example.FitnessTrackingApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Fitness Tracker API",
                description = "This is the updated documentation for all fitness tracking endpoints."
        )
)
@SpringBootApplication
public class FitnessTrackingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessTrackingAppApplication.class, args);
    }
}
