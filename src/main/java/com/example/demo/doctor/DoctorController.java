package com.example.demo.doctor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public void addNewDoctor(@RequestBody Doctor doctor) {
        service.addNewDoctor(doctor);
    }
}
