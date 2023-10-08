package com.example.demo.doctor.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.dto.DoctorDto;
import com.example.demo.doctor.entity.Doctor;
import com.example.demo.doctor.entity.FileData;
import com.example.demo.doctor.services.DoctorService;

@RestController
@RequestMapping(path = "api/v1/doctors")
public class DoctorController {
    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return service.getDoctors();
    }

    @PostMapping()
    public Map<String, Object> addNewDoctor(@RequestBody DoctorDto doctorDto)
            throws NumberFormatException, IOException {
        Map<String, Object> result = service.addNewDoctor(doctorDto);

        return result;
    }

    @PostMapping("/addImage")
    public FileData addDoctorImage(
            @RequestParam("image") MultipartFile file)

            throws NumberFormatException, IOException {
        FileData image = service.uploadImageToFileSystem(file);

        if (image != null) {
            return image;

        }
        return null;

    }

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long id) {
        service.deleteDoctor(id);
    }

    // @PutMapping(path = "{doctorId}")
    // public void updateDoctor(@PathVariable("doctorId") Long id,
    // @RequestParam(required = false) String name,
    // @RequestParam(required = false) String dept,
    // @RequestParam(required = false) String symptoms,
    // @RequestParam(required = false) String degrees) {
    // service.updateDoctor(id, name, dept, symptoms, degrees);
    // }

    // @PostMapping(path = "{doctorId}")
    // public void updateDoctor(@PathVariable("doctorId") Long id, @RequestBody
    // Doctor doctor) {
    // service.updateDoctor(id, doctor);
    // }
}
