package com.example.demo.ambulance.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;
import com.example.demo.ambulance.dto.AddAmbulanceDto;
import com.example.demo.ambulance.dto.EditAmbulanceDto;
import com.example.demo.ambulance.dto.SendAmbulanceDataDto;
import com.example.demo.ambulance.entity.Ambulance;
import com.example.demo.ambulance.services.AmbulanceService;
import com.example.demo.ambulance.entity.AmbulanceImage;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/ambulances")
public class AmbulanceController {
    private final AmbulanceService service;

    public AmbulanceController(AmbulanceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAmbulances() {
        SendAmbulanceDataDto department = new SendAmbulanceDataDto(service.getAmbulances());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the ambulances",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewAmbulance(@RequestBody @Valid AddAmbulanceDto ambulance) {
        if (service.addNewAmbulance(ambulance)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Ambulance added successfully",
                    new SuccessMessageModel("Successfully added an Ambulance", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Ambulance exists already",
                new SuccessMessageModel("Ambulance exists already", false));
    }

    @PostMapping("/addImage")
    public AmbulanceImage addAmbulanceImage(@RequestParam("image") MultipartFile file)
            throws NumberFormatException, IOException {
        AmbulanceImage image = service.uploadImageToFileSystem(file);
        if (image != null) {
            return image;
        }
        return null;
    }

    @DeleteMapping(path = "{ambulanceId}")
    public void deleteAmbulance(@PathVariable("ambulanceId") Long id) {
        service.deleteAmbulance(id);
    }

    @PostMapping(path = "/update/{ambulanceId}")
    public ResponseEntity<Object> updateAmbulance(@PathVariable("ambulanceId") Long id,
            @RequestBody EditAmbulanceDto ambulance) {
        Ambulance isOkay = service.updateAmbulance(id, ambulance);
        if (isOkay != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    "Ambulance updated successfully",
                    isOkay);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Ambulance already exists",
                null);
    }

    @GetMapping("/getImage/{ambulanceId}")
    public ResponseEntity<?> getDepartments(@PathVariable("ambulanceId") Long id) throws IOException {
        byte[] image = service.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

}
