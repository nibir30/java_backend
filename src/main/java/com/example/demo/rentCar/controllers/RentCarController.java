package com.example.demo.rentCar.controllers;

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
import com.example.demo.rentCar.dto.AddRentCarDto;
import com.example.demo.rentCar.dto.EditRentCarDto;
import com.example.demo.rentCar.dto.SendRentCarDataDto;
import com.example.demo.rentCar.entity.RentCar;
import com.example.demo.rentCar.services.RentCarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/rentCars")
public class RentCarController {
    private final RentCarService service;

    public RentCarController(RentCarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getRentCars() {
        SendRentCarDataDto department = new SendRentCarDataDto(service.getRentCars());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the rentCars",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewRentCar(@RequestBody @Valid AddRentCarDto rentCar) {
        if (service.addNewRentCar(rentCar)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "RentCar added successfully",
                    new SuccessMessageModel("Successfully added an RentCar", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.CONFLICT, false,
                "RentCar exists already",
                new SuccessMessageModel("RentCar exists already", false));
    }

    @DeleteMapping(path = "{rentCarId}")
    public void deleteRentCar(@PathVariable("rentCarId") Long id) {
        service.deleteRentCar(id);
    }

    @PostMapping(path = "/update/{rentCarId}")
    public ResponseEntity<Object> updateRentCar(@PathVariable("rentCarId") Long id,
            @RequestBody EditRentCarDto rentCar) {
        RentCar isOkay = service.updateRentCar(id, rentCar);
        if (isOkay != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "RentCar updated successfully",
                    isOkay);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "RentCar already exists",
                null);
    }

}
