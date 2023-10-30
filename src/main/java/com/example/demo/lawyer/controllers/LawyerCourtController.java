package com.example.demo.lawyer.controllers;

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
import com.example.demo.lawyer.dto.AddCourtDto;
import com.example.demo.lawyer.dto.EditCourtDto;
import com.example.demo.lawyer.dto.SendCourtDataDto;
import com.example.demo.lawyer.services.LawyerCourtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/lawyers/courts")
public class LawyerCourtController {
    private final LawyerCourtService service;

    public LawyerCourtController(LawyerCourtService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getLawyerCourts() {
        SendCourtDataDto department = new SendCourtDataDto(service.getCourts());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the laywer courts",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewLawyerCourt(@RequestBody @Valid AddCourtDto court) {
        if (service.addNewCourt(court)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Practice Court added successfully",
                    new SuccessMessageModel("Successfully added a Practice Court", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Practice Court exists already",
                new SuccessMessageModel("Practice Court exists already", false));
    }

    @DeleteMapping(path = "{courtId}")
    public void deleteLawyerCourt(@PathVariable("courtId") Long id) {
        service.deleteCourt(id);
    }

    @PostMapping(path = "/update/{courtId}")
    public ResponseEntity<Object> updateCourt(@PathVariable("courtId") Long id,
            @RequestBody EditCourtDto court) {
        boolean isOkay = service.updateCourt(id, court);
        if (isOkay) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Practice Court updated successfully",
                    new SuccessMessageModel("Successfully updated a Practice Court", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Practice Court already exists",
                new SuccessMessageModel("Practice Court already exists", false));
    }

}
