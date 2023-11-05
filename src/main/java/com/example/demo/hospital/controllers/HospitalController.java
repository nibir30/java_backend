package com.example.demo.hospital.controllers;

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
import com.example.demo.hospital.dto.AllHospitalDto;
import com.example.demo.hospital.dto.EditHospitalDto;
import com.example.demo.hospital.dto.HospitalDto;
import com.example.demo.hospital.entity.Hospital;
import com.example.demo.hospital.services.HospitalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/hospitals")
public class HospitalController {
    private final HospitalService service;

    public HospitalController(HospitalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getHospitals() {
        AllHospitalDto department = new AllHospitalDto(service.getHospitals());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the hospitals",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewHospital(@RequestBody @Valid HospitalDto hospital) {
        if (service.addNewHospital(hospital)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Hospital added successfully",
                    new SuccessMessageModel("Successfully added an Hospital", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Hospital exists already",
                new SuccessMessageModel("Hospital exists already", false));
    }

    @DeleteMapping(path = "{hospitalId}")
    public void deleteHospital(@PathVariable("hospitalId") Long id) {
        service.deleteHospital(id);
    }

    @PostMapping(path = "/update/{hospitalId}")
    public ResponseEntity<Object> updateHospital(@PathVariable("hospitalId") Long id,
            @RequestBody EditHospitalDto hospital) {
        Hospital isOkay = service.updateHospital(id, hospital);
        if (isOkay != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Hospital updated successfully",
                    isOkay);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Hospital already exists",
                null);
    }

}
