package com.example.demo.doctor.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.dto.DoctorDto;
import com.example.demo.doctor.entity.Degree;
import com.example.demo.doctor.entity.Doctor;
import com.example.demo.doctor.entity.DoctorImage;
import com.example.demo.doctor.entity.Symptom;
import com.example.demo.doctor.repositories.DegreeRepository;
import com.example.demo.doctor.repositories.DepartmentRepository;
import com.example.demo.doctor.repositories.DoctorRepository;
import com.example.demo.doctor.repositories.ImageDoctorRepository;
import com.example.demo.doctor.repositories.SymptomRepository;
import com.example.demo.helpers.DebugHelper;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final SymptomRepository symptomRepository;
    private final DegreeRepository degreeRepository;
    private final ImageDoctorRepository imageRepository;

    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository,
            SymptomRepository symptomRepository, DegreeRepository degreeRepository,
            ImageDoctorRepository imageRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.symptomRepository = symptomRepository;
        this.degreeRepository = degreeRepository;
        this.imageRepository = imageRepository;
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
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

        if (final_data != null || file != null) {
            return final_data;
        }
        return null;

    }

    // public Map<String, String> addNewDoctor(Doctor doctor) {

    public Map<String, Object> addNewDoctor(DoctorDto doctorDto) {

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
                return result;
            }

            doctor.setId(doctorDto.getId());
            BeanUtils.copyProperties(doctorDto, doctor);
            List<Symptom> symptoms = new ArrayList<>();
            List<Degree> degrees = new ArrayList<>();

            for (Long symptomId : doctorDto.getSymptomIds()) {
                symptoms.add(symptomRepository.getReferenceById(symptomId));
            }
            for (Long degreeId : doctorDto.getDegreeIds()) {
                degrees.add(degreeRepository.getReferenceById(degreeId));
            }
            doctor.setDegree(degrees);
            doctor.setSymptom(symptoms);
            doctor.setDept(departmentRepository.getReferenceById(doctorDto.getDeptId()));

            Doctor savedDoctor = doctorRepository.save(doctor);
            DebugHelper.printData(savedDoctor.toString());
            result.put("id", savedDoctor.getId());
            result.put("message", "Doctor added successfully");
            return result;

        } else {
            if (!isDeptOk) {
                result.put("id", null);
                result.put("message", "Department does not exist");
                return result;
            }
            if (!isSymptomOk) {
                result.put("id", null);
                result.put("message", "Symptom does not exist");
                return result;
            }
            if (!isDegreeOk) {
                result.put("id", null);
                result.put("message", "Degree does not exist");
                return result;
            }
        }
        result.put("id", null);
        result.put("message", "Error");
        return result;
    }

    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Doctor does not exit");
        }
    }

    // @Transactional
    // public void updateDoctor(Long id, String name, Long deptId, String symptoms,
    // String degrees) {
    // Doctor doctor = doctorRepository.findById(id)
    // .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));

    // if (name != null && name.length() > 0) {
    // doctor.setName(name);
    // }
    // // if (dept != null && dept.length() > 0) {
    // // doctor.setDept(dept);
    // // }
    // if (deptId != null) {
    // Department dept = departmentRepository.findById(deptId)
    // .orElseThrow(() -> new IllegalStateException("Department does not exit"));
    // doctor.setDept(dept);
    // }

    // if (symptoms != null && symptoms.length() > 0) {
    // doctor.setSymptoms(symptoms);
    // }
    // if (degrees != null && degrees.length() > 0) {
    // doctor.setDegrees(degrees);
    // }
    // }

    // @Transactional
    // public void updateDoctor(Long id, Doctor doctor) {
    // Doctor newDoctor = doctorRepository.findById(id)
    // .orElseThrow(() -> new IllegalStateException("Doctor does not exit"));
    // boolean isDeptOk = departmentRepository.existsById(doctor.getDept_id());
    // boolean isSymptomOk = true;
    // boolean isDegreeOk = true;

    // if (doctor.getSymptom() != null) {
    // for (Long symptomId : doctor.getSymptom()) {
    // if (!symptomRepository.existsById(symptomId)) {
    // isSymptomOk = false;
    // break;
    // }
    // }
    // }
    // if (doctor.getDegree() != null) {
    // for (Long degreeId : doctor.getDegree()) {
    // if (!degreeRepository.existsById(degreeId)) {
    // isSymptomOk = false;
    // break;
    // }
    // }
    // }

    // // if (doctor.getDept() != null) {
    // // newDoctor.setDept(doctor.getDept());
    // // }

    // if (isDeptOk && isSymptomOk && isDegreeOk) {

    // if (doctor.getName() != null && doctor.getName().length() > 0) {
    // newDoctor.setName(doctor.getName());
    // }
    // if (doctor.getDept_id() != null) {
    // Department dept = departmentRepository.findById(doctor.getDept_id())
    // .orElseThrow(() -> new IllegalStateException("Department does not exit"));
    // newDoctor.setDept_id(doctor.getDept_id());
    // }
    // if (doctor.getDegree() != null && doctor.getDegree().size() > 0) {
    // newDoctor.setDegree(doctor.getDegree());
    // }
    // if (doctor.getSymptom() != null && doctor.getSymptom().size() > 0) {
    // newDoctor.setSymptom(doctor.getSymptom());
    // }

    // System.out.println(newDoctor);

    // }

    // }

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
