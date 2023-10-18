package com.example.demo.lawyer.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.lawyer.dto.AddLawyerDto;
import com.example.demo.lawyer.dto.AllLawyerDto;
import com.example.demo.lawyer.dto.EditLawyerDto;
import com.example.demo.lawyer.entity.Lawyer;
import com.example.demo.lawyer.services.LawyerService;
import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;

@RestController
@RequestMapping(path = "api/v1/lawyers")
public class LawyerController {
    private final LawyerService service;

    public LawyerController(LawyerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getLawyers() {
        AllLawyerDto lawyerDto = new AllLawyerDto(service.getLawyers());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Lawyer viewed successfully", lawyerDto);
    }

    @PostMapping()
    public ResponseEntity<Object> addNewLawyer(@RequestBody AddLawyerDto lawyerDto)
            throws NumberFormatException, IOException {

        boolean isOkay = service.addNewLawyer(lawyerDto);

        if (isOkay) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Lawyer created successfully",
                    new SuccessMessageModel("Lawyer created successfully", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Practice Court already exists",
                new SuccessMessageModel("Practice Court already exists", false));

    }

    @DeleteMapping(path = "{lawyerId}")
    public void deleteLawyer(@PathVariable("lawyerId") Long id) {
        service.deleteLawyer(id);
    }

    @PostMapping(path = "/update/{lawyerId}")
    public ResponseEntity<Object> updateLawyer(@PathVariable("lawyerId") Long id,
            @RequestBody EditLawyerDto lawyer) {
        Lawyer lawyer2 = service.updateLawyer(id, lawyer);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Lawyer updated successfully",
                lawyer2);
        // service.updateLawyer(id, lawyer);
    }

    @GetMapping(path = "/type/{typeId}")
    public ResponseEntity<Object> getLawyerstype(@PathVariable("typeId") Long id) {
        AllLawyerDto lawyers = new AllLawyerDto(service.getLawyersbyType(id));
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Lawyers viewed successfully", lawyers);
    }

}
