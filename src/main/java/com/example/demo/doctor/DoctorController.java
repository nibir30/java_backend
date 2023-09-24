package com.example.demo.doctor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long id) {
        service.deleteDoctor(id);
    }

    @PutMapping(path = "{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dept,
            @RequestParam(required = false) String symptoms,
            @RequestParam(required = false) String degrees) {
        service.updateDoctor(id, name, dept, symptoms, degrees);
    }

    @PostMapping(path = "{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Long id, @RequestBody Doctor doctor) {
        service.updateDoctor(id, doctor);
    }
}
