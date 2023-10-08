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
import com.example.demo.doctor.dto.EditDoctorDto;
import com.example.demo.doctor.entity.Doctor;
import com.example.demo.doctor.entity.DoctorImage;
import com.example.demo.doctor.services.DoctorService;
import com.example.demo.helpers.DebugHelper;

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

        // return ResponseEntity.ok(result);
        return result;

    }

    @PostMapping("/addImage")
    public DoctorImage addDoctorImage(
            @RequestParam("image") MultipartFile file)

            throws NumberFormatException, IOException {
        DoctorImage image = service.uploadImageToFileSystem(file);

        if (image != null) {
            return image;

        }
        return null;

    }

    @PostMapping("/updateImage")
    public DoctorImage updateDoctorImage(
            @RequestParam("image") MultipartFile file, @RequestParam("id") String id)

            throws NumberFormatException, IOException {
        DoctorImage image = service.updateImageFromFileSystem(file, id);

        if (image != null) {
            return image;

        }
        return null;

    }

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long id) {
        service.deleteDoctor(id);
    }

    @PostMapping(path = "/update")
    // @PostMapping(path = "/{doctorId}")

    public void updateDoctor(
            // @PathVariable("doctorId") Long id,
            @RequestBody EditDoctorDto doctor) {
        DebugHelper.printData(doctor.toString());
        service.updateDoctor(doctor);
        // service.updateDoctor(id, doctor);

    }
}
