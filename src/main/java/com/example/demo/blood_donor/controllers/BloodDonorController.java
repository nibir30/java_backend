package com.example.demo.blood_donor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.blood_donor.dtos.AddDonorDto;
import com.example.demo.blood_donor.dtos.SendDonorDataDto;
import com.example.demo.blood_donor.services.BloodDonorService;
import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/bloodDonors")
public class BloodDonorController {
    @Autowired
    BloodDonorService service;

    // public BloodDonorController(BloodDonorService service) {
    // this.service = service;
    // }

    @GetMapping
    public ResponseEntity<Object> getBloodDonors() {
        SendDonorDataDto department = new SendDonorDataDto(service.getBloodDonors());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the bloodDonors",
                department);
    }

    @GetMapping(path = "{bloodDonorId}")
    public ResponseEntity<Object> getBloodDonorsByOrganization(@PathVariable("bloodDonorId") Long id) {
        SendDonorDataDto department = new SendDonorDataDto(service.getBloodDonorsByOrganization(id));
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the bloodDonors",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewBloodDonor(@RequestBody @Valid AddDonorDto bloodDonor) {
        if (service.addBloodDonor(bloodDonor)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "BloodDonor added successfully",
                    new SuccessMessageModel("Successfully added an BloodDonor", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "BloodDonor exists already",
                new SuccessMessageModel("BloodDonor exists already", false));
    }

    @DeleteMapping(path = "{bloodDonorId}")
    public void deleteBloodDonor(@PathVariable("bloodDonorId") Long id) {
        service.deleteBloodDonor(id);
    }

    // @PostMapping(path = "/update/{bloodDonorId}")
    // public ResponseEntity<Object>
    // updateBloodDonor(@PathVariable("bloodDonorId")
    // Long
    // id,
    // @RequestBody EditBloodDonorDto bloodDonor) {
    // BloodDonor isOkay = service.updateBloodDonor(id, bloodDonor);
    // if (isOkay != null) {
    // return ResponseHandler.generateResponse(HttpStatus.OK, true,
    // "BloodDonor updated successfully",
    // isOkay);
    // }
    // return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
    // "BloodDonor already exists",
    // null);
    // }

}
