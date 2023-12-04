package com.example.demo.generic;

import com.example.demo.ambulance.dto.SendAmbulanceDataDto;
import com.example.demo.helpers.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "welcome")
public class GenericController {
    @GetMapping
    public String getWelocome() {
        return "Welcome to Tangail Helpline";
    }
}
