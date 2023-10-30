package com.example.demo.coaching.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;
import com.example.demo.coaching.dto.AddCoachingDto;
import com.example.demo.coaching.dto.EditCoachingDto;
import com.example.demo.coaching.dto.SendCoachingDataDto;
import com.example.demo.coaching.entity.Coaching;
import com.example.demo.coaching.services.CoachingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/coachings")
public class CoachingController {
    private final CoachingService service;

    public CoachingController(CoachingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getCoachings() {
        SendCoachingDataDto department = new SendCoachingDataDto(service.getCoachings());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the coachings",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewCoaching(@RequestBody @Valid AddCoachingDto coaching) {
        if (service.addNewCoaching(coaching)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Coaching added successfully",
                    new SuccessMessageModel("Successfully added an Coaching", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Coaching exists already",
                new SuccessMessageModel("Coaching exists already", false));
    }

    @DeleteMapping(path = "{coachingId}")
    public void deleteCoaching(@PathVariable("coachingId") Long id) {
        service.deleteCoaching(id);
    }

    @PostMapping(path = "/update/{coachingId}")
    public ResponseEntity<Object> updateCoaching(@PathVariable("coachingId") Long id,
            @RequestBody EditCoachingDto coaching) {
        Coaching isOkay = service.updateCoaching(id, coaching);
        if (isOkay != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Coaching updated successfully",
                    isOkay);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Coaching already exists",
                null);
    }

}
