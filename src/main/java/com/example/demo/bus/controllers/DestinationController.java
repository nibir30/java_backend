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

import com.example.demo.bus.dtos.AddDestinationDto;
import com.example.demo.bus.dtos.SendDestinationDataDto;
import com.example.demo.bus.services.DestinationService;
import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/bus/destinations")
public class DestinationController {
    private final DestinationService service;

    public DestinationController(DestinationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getDestinations() {
        SendDestinationDataDto department = new SendDestinationDataDto(service.getDestination());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the destinations",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewDestination(@RequestBody @Valid AddDestinationDto destinationDto) {
        if (service.addNewDestination(destinationDto)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Destination added successfully",
                    new SuccessMessageModel("Successfully added a Destination", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Destination exists already",
                new SuccessMessageModel("Destination exists already", false));
    }

    @DeleteMapping(path = "{destinationId}")
    public void deleteDestination(@PathVariable("destinationId") Long id) {
        service.deleteDestination(id);
    }

    @PostMapping(path = "/update/{destinationId}")
    public ResponseEntity<Object> updateCourt(@PathVariable("destinationId") Long id,
            @RequestBody AddDestinationDto court) {
        boolean isOkay = service.updateDestination(id, court);
        if (isOkay) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Destination updated successfully",
                    new SuccessMessageModel("Successfully updated a Destination", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Destination already exists",
                new SuccessMessageModel("Destination already exists", false));
    }

}
