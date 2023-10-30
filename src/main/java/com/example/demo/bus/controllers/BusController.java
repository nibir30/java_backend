package com.example.demo.bus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bus.dtos.AddBusDto;
import com.example.demo.bus.dtos.EditBusDto;
import com.example.demo.bus.dtos.SendBusDataDto;
import com.example.demo.bus.services.BusService;
import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/bus")
public class BusController {
    private final BusService service;

    public BusController(BusService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getBuss() {
        SendBusDataDto department = new SendBusDataDto(service.getBus());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the bus",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewBus(@RequestBody @Valid AddBusDto busDto) {
        if (service.addNewBus(busDto)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Bus added successfully",
                    new SuccessMessageModel("Successfully added a Bus", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Bus exists already",
                new SuccessMessageModel("Bus exists already", false));
    }

    @DeleteMapping(path = "{busId}")
    public void deleteBus(@PathVariable("busId") Long id) {
        service.deleteBus(id);
    }

    @PostMapping(path = "/update/{busId}")
    public ResponseEntity<Object> updateCourt(@PathVariable("busId") Long id,
            @RequestBody EditBusDto court) {
        if (service.updateBus(id, court)) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Bus updated successfully",
                    new SuccessMessageModel("Successfully updated a Bus", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Bus already exists",
                new SuccessMessageModel("Bus already exists", false));
    }

}
