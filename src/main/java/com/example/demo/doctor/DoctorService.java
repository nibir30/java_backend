package com.example.demo.doctor;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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

    public void deleteDoctor(Long id) {

        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Doctor does not exit");
        }
    }

    @Transactional
    public void updateDoctor(Long id, String name, String dept, String symptoms, String degrees) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));

        if (name != null && name.length() > 0) {
            doctor.setName(name);
        }
        if (dept != null && dept.length() > 0) {
            doctor.setDept(dept);
        }
        if (symptoms != null && symptoms.length() > 0) {
            doctor.setSymptoms(symptoms);
        }
        if (degrees != null && degrees.length() > 0) {
            doctor.setDegrees(degrees);
        }
    }

    @Transactional
    public void updateDoctor(Long id, Doctor doctor) {
        Doctor newDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));

        System.out.println(newDoctor);

        if (doctor.getName() != null && doctor.getName().length() > 0) {
            newDoctor.setName(doctor.getName());
        }
        if (doctor.getDegrees() != null && doctor.getDegrees().length() > 0) {
            newDoctor.setDegrees(doctor.getDegrees());
        }
        if (doctor.getSymptoms() != null && doctor.getSymptoms().length() > 0) {
            newDoctor.setSymptoms(doctor.getSymptoms());
        }
        if (doctor.getDept() != null && doctor.getDept().length() > 0) {
            newDoctor.setDept(doctor.getDept());
        }
    }
}
