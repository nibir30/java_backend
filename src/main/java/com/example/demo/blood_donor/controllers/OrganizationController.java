package com.example.demo.blood_donor.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.blood_donor.dtos.AddOrganizationDto;
import com.example.demo.blood_donor.dtos.SendOrganizationDataDto;
import com.example.demo.blood_donor.entity.OrganizationImage;
import com.example.demo.blood_donor.services.OrganizationService;
import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/organizations")
public class OrganizationController {
    @Autowired
    OrganizationService service;

    // public OrganizationController(OrganizationService service) {
    // this.service = service;
    // }

    @GetMapping
    public ResponseEntity<Object> getOrganizations() {
        SendOrganizationDataDto department = new SendOrganizationDataDto(service.getOrganizations());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the organizations",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewOrganization(@RequestBody @Valid AddOrganizationDto organization) {
        if (service.addNewOrganization(organization)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "Organization added successfully",
                    new SuccessMessageModel("Successfully added an Organization", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "Organization exists already",
                new SuccessMessageModel("Organization exists already", false));
    }

    @DeleteMapping(path = "{organizationId}")
    public void deleteOrganization(@PathVariable("organizationId") Long id) {
        service.deleteOrganization(id);
    }

    // @PostMapping(path = "/update/{organizationId}")
    // public ResponseEntity<Object>
    // updateOrganization(@PathVariable("organizationId")
    // Long
    // id,
    // @RequestBody EditOrganizationDto organization) {
    // Organization isOkay = service.updateOrganization(id, organization);
    // if (isOkay != null) {
    // return ResponseHandler.generateResponse(HttpStatus.OK, true,
    // "Organization updated successfully",
    // isOkay);
    // }
    // return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
    // "Organization already exists",
    // null);
    // }

    @GetMapping("/getImage/{organizationId}")
    public ResponseEntity<?> getDepartments(@PathVariable("organizationId") Long id) throws IOException {
        byte[] image = service.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

    @PostMapping("/addImage")
    public OrganizationImage addOrganizationImage(
            @RequestParam("image") MultipartFile file)

            throws NumberFormatException, IOException {
        OrganizationImage image = service.uploadImageToFileSystem(file);

        if (image != null) {
            return image;

        }
        return null;

    }

    @PostMapping("/updateImage")
    public OrganizationImage updateOrganizationImage(
            @RequestParam("image") MultipartFile file, @RequestParam("id") String id)
            throws NumberFormatException, IOException {
        OrganizationImage image = service.updateImageFromFileSystem(file, id);
        if (image != null) {
            return image;
        }
        return null;
    }

}
