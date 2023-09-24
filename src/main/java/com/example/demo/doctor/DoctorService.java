package com.example.demo.doctor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public void addNewDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        System.out.println(doctor);
    }
}
