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
import com.example.demo.lawyer.dto.AddPracticeTypeDto;
import com.example.demo.lawyer.dto.EditPracticeTypeDto;
import com.example.demo.lawyer.dto.SendPracticeTypeDataDto;
import com.example.demo.lawyer.services.LawyerPracticeTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/lawyers/practiceTypes")
public class LawyerPracticeTypeController {
    private final LawyerPracticeTypeService service;

    public LawyerPracticeTypeController(LawyerPracticeTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getLawyerPracticeTypes() {
        SendPracticeTypeDataDto department = new SendPracticeTypeDataDto(service.getPracticeTypes());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the laywer types",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewLawyerPracticeType(@RequestBody @Valid AddPracticeTypeDto practiceType) {
        if (service.addNewPracticeType(practiceType)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Practice Type added successfully",
                    new SuccessMessageModel("Successfully added a Practice Type", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Practice Type exists already",
                new SuccessMessageModel("Practice Type exists already", false));
    }

    @DeleteMapping(path = "{practiceTypeId}")
    public void deleteLawyerPracticeType(@PathVariable("practiceTypeId") Long id) {
        service.deletePracticeType(id);
    }

    @PostMapping(path = "/update/{practiceTypeId}")
    public ResponseEntity<Object> updatePracticeType(@PathVariable("practiceTypeId") Long id,
            @RequestBody EditPracticeTypeDto practiceType) {
        boolean isOkay = service.updatePracticeType(id, practiceType);
        if (isOkay) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Practice Type updated successfully",
                    new SuccessMessageModel("Successfully updated a Practice Type", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Practice Type couldnt be found",
                new SuccessMessageModel("Practice Type couldnt be found", false));
    }

}
