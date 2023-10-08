package com.example.demo.doctor.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.dto.DoctorDto;
import com.example.demo.doctor.dto.EditDoctorDto;
import com.example.demo.doctor.entity.*;
import com.example.demo.doctor.repositories.*;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final SymptomRepository symptomRepository;
    private final DegreeRepository degreeRepository;
    private final ImageDoctorRepository imageRepository;

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public DoctorImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<DoctorImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            DoctorImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
    }

    public DoctorImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        DoctorImage data = DoctorImage.builder()
                .name("DOCTOR" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("doctors")
                .build();
        DoctorImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String filePath = FOLDER_PATH + "DOCTOR" + savedFile.getId().toString() + file.getOriginalFilename();

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName("DOCTOR" + savedFile.getId().toString() + file.getOriginalFilename());

        DoctorImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public Map<String, Object> addNewDoctor(DoctorDto doctorDto) {

        DebugHelper.printData(doctorDto.toString());

        boolean isSymptomOk = true;
        boolean isDegreeOk = true;
        boolean isDeptOk = departmentRepository.existsById(doctorDto.getDeptId());

        DebugHelper.printData(doctorDto.toString());

        for (Long symptomId : doctorDto.getSymptomIds()) {
            if (!symptomRepository.existsById(symptomId)) {
                isSymptomOk = false;
                break;
            }
        }
        for (Long degreeId : doctorDto.getDegreeIds()) {
            if (!degreeRepository.existsById(degreeId)) {
                isDegreeOk = false;
                break;
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (isDeptOk && isSymptomOk && isDegreeOk) {
            Doctor doctor = new Doctor();
            if (doctorRepository.existsById(doctorDto.getId())) {
                result.put("id", doctorDto.getId());
                result.put("message", "Doctor already exists");
                result.put("isSuccess", false);

                return result;
            }

            doctor.setId(doctorDto.getId());
            BeanUtils.copyProperties(doctorDto, doctor);
            List<Symptom> symptoms = new ArrayList<>();
            List<Degree> degrees = new ArrayList<>();

            for (Long symptomId : doctorDto.getSymptomIds()) {
                symptoms.add(symptomRepository.findById(symptomId)
                        .orElseThrow(() -> new IllegalStateException("symptom does not exit")));
            }
            for (Long degreeId : doctorDto.getDegreeIds()) {
                degrees.add(degreeRepository.findById(degreeId)
                        .orElseThrow(() -> new IllegalStateException("degree does not exit")));
            }
            doctor.setDegree(degrees);
            doctor.setSymptom(symptoms);
            doctor.setDept(departmentRepository.findById(doctorDto.getDeptId())
                    .orElseThrow(() -> new IllegalStateException("department does not exit")));

            Doctor savedDoctor = doctorRepository.save(doctor);
            DebugHelper.printData(savedDoctor.toString());
            result.put("id", savedDoctor.getId());
            result.put("message", "Doctor added successfully");
            result.put("isSuccess", true);

            return result;

        } else {
            if (!isDeptOk) {
                result.put("id", null);
                result.put("message", "Department does not exist");
                result.put("isSuccess", false);

                return result;
            }
            if (!isSymptomOk) {
                result.put("id", null);
                result.put("message", "Symptom does not exist");
                result.put("isSuccess", false);

                return result;
            }
            if (!isDegreeOk) {
                result.put("id", null);
                result.put("message", "Degree does not exist");
                result.put("isSuccess", false);

                return result;
            }
        }
        result.put("id", null);
        result.put("message", "Error");
        result.put("isSuccess", false);

        return result;
    }

    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Doctor does not exit");
        }
    }

    @Transactional
    // public void updateDoctor(Long id, DoctorDto doctor) {

    public Doctor updateDoctor(EditDoctorDto doctor) {

        Doctor newDoctor = doctorRepository.findById(doctor.getId())
                .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));
        DebugHelper.printData(newDoctor.toString());

        boolean isDeptOk = true;
        boolean isSymptomOk = true;
        boolean isDegreeOk = true;

        List<Symptom> symptoms = new ArrayList<>();
        List<Degree> degrees = new ArrayList<>();

        if (doctor.getSymptomIds() != null) {
            for (Long symptomId : doctor.getSymptomIds()) {

                symptoms.add(symptomRepository.findById(symptomId)
                        .orElseThrow(() -> new IllegalStateException("Symptom does not exit")));
            }
        }
        if (doctor.getDegreeIds() != null) {
            for (Long degreeId : doctor.getDegreeIds()) {
                Degree newDegree = degreeRepository.findById(degreeId)
                        .orElseThrow(() -> new IllegalStateException("Degree does not exit"));
                degrees.add(
                        newDegree);

            }
        }

        if (isDeptOk && isSymptomOk && isDegreeOk) {
            if (doctor.getName() != null && doctor.getName().length() > 0) {
                newDoctor.setName(doctor.getName());
            }
            if (doctor.getDeptId() != null) {
                Department dept = departmentRepository.findById(doctor.getDeptId())
                        .orElseThrow(() -> new IllegalStateException("Department does not exit"));
                newDoctor.setDept(dept);
            }
            if (doctor.getDegreeIds() != null) {
                newDoctor.setDegree(degrees);
            }
            if (doctor.getSymptomIds() != null) {
                newDoctor.setSymptom(symptoms);
            }
            System.out.println(newDoctor.toString());

            return newDoctor;
        }

        return null;

    }

    // private final String FOLDER_PATH =
    // "D:/dev/backend/java/java_backend/src/images/doctors/";
    private final String FOLDER_PATH = "D:/Dev/Backend/Java/tngl/java_backend/src/images/doctors/";

    public String uploadImageToFileSystem(Long id, MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + "DOCTOR" + id + file.getOriginalFilename();

        Doctor newDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));
        // Doctor fileData = doctorRepository.save(Doctor.builder()
        // // .name(file.getOriginalFilename())
        // // .type(file.getContentType())
        // .imageFilePath(filePath).build());
        // System.out.println(newDoctor);
        newDoctor.setImage_file_path(filePath);
        System.out.println(newDoctor);
        System.out.println(filePath);

        file.transferTo(new File(filePath));

        // if (fileData != null) {
        return "Successful";
        // }
        // return "Error saving file";
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Doctor> fileData = doctorRepository.findByName(fileName);
        String filePath = fileData.get().getImage_file_path();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
