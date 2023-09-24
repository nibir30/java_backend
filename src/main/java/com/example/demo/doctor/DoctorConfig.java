package com.example.demo.doctor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DoctorConfig {

    @Bean
    CommandLineRunner commandLineRunner(DoctorRepository repository) {
        return args -> {
            Doctor nibir = new Doctor("Nibir", "Medicine", "Fever", "MBBS");
            Doctor arnob = new Doctor("arnob", "Medicine", "Fever", "MBBS");
            repository.saveAll(
                    // List.of(nibir, arnob)
                    java.util.List.of(nibir, arnob));
        };

    }
}
