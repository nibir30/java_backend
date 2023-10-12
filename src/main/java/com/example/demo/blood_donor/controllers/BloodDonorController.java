package com.example.demo.blood_donor.controllers;

import java.util.Map;

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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/bloodDonors")
@AllArgsConstructor
public class BloodDonorController {
    private final BloodDonorService service;

    @GetMapping
    public SendDonorDataDto getBloodDonors() {
        SendDonorDataDto donors = new SendDonorDataDto(service.getBloodDonors());
        return donors;
    }

    @PostMapping
    public Map<String, Object> addNewBloodDonor(@RequestBody AddDonorDto donor) {
        Map<String, Object> result = service.addNewBloodDonor(donor);
        return result;
    }

    @DeleteMapping(path = "{donorId}")
    public void deleteBloodDonor(@PathVariable("donorId") Long id) {
        service.deleteBloodDonor(id);
    }

    @PostMapping(path = "{donorId}")
    public void updateBloodDonor(@PathVariable("donorId") Long id, @RequestBody AddDonorDto donor) {
        service.updateBloodDonor(id, donor);
    }
}
