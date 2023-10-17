package com.example.demo.bloodBank.controllers;

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
import com.example.demo.bloodBank.dto.AddBloodBankDto;
import com.example.demo.bloodBank.dto.EditBloodBankDto;
import com.example.demo.bloodBank.dto.SendBloodBankDataDto;
import com.example.demo.bloodBank.entity.BloodBank;
import com.example.demo.bloodBank.services.BloodBankService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/bloodBanks")
public class BloodBankController {
    private final BloodBankService service;

    public BloodBankController(BloodBankService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getBloodBanks() {
        SendBloodBankDataDto department = new SendBloodBankDataDto(service.getBloodBanks());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the bloodBanks",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewBloodBank(@RequestBody @Valid AddBloodBankDto bloodBank) {
        if (service.addNewBloodBank(bloodBank)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "BloodBank added successfully",
                    new SuccessMessageModel("Successfully added an BloodBank", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.CONFLICT, false,
                "BloodBank exists already",
                new SuccessMessageModel("BloodBank exists already", false));
    }

    @DeleteMapping(path = "{bloodBankId}")
    public void deleteBloodBank(@PathVariable("bloodBankId") Long id) {
        service.deleteBloodBank(id);
    }

    @PostMapping(path = "/update/{bloodBankId}")
    public ResponseEntity<Object> updateBloodBank(@PathVariable("bloodBankId") Long id,
            @RequestBody EditBloodBankDto bloodBank) {
        BloodBank isOkay = service.updateBloodBank(id, bloodBank);
        if (isOkay != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "BloodBank updated successfully",
                    isOkay);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "BloodBank already exists",
                null);
    }

}
