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

import com.example.demo.doctor.entity.Doctor;
import com.example.demo.doctor.entity.FileData;
import com.example.demo.doctor.services.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "api/v1/doctors")
public class DoctorController {
    private final DoctorService service;

    // @Autowired
    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return service.getDoctors();
    }

    // @PostMapping
    @PostMapping()
    // public Map<String, String> addNewDoctor(@RequestBody Doctor doctor,
    // @RequestParam("image") MultipartFile file)
    public Map<String, String> addNewDoctor(@RequestParam("doctor") String doctor,
            @RequestParam("image") MultipartFile file)

            // (@RequestPart("doctor") Doctor doctor,
            // @RequestPart("image") MultipartFile file)

            throws NumberFormatException, IOException {

        // @Autowired
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = service.addNewDoctor(objectMapper.readValue(doctor, Doctor.class));

        if (result.get("id") != null) {
            service.uploadImageToFileSystem(Long.parseLong(result.get("id")), file);
        }
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

    @PostMapping(path = "{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Long id, @RequestBody Doctor doctor) {
        service.updateDoctor(id, doctor);
    }
}
