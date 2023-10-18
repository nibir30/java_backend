package com.example.demo.hospital.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.hospital.dto.HospitalDto;
import com.example.demo.hospital.dto.EditHospitalDto;
import com.example.demo.hospital.entity.*;
import com.example.demo.hospital.repositories.*;
import com.example.demo.helpers.AppConstant;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalDepartmentRepository departmentRepository;
    private final ImageHospitalRepository imageRepository;

    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    public HospitalImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<HospitalImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            HospitalImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
    }

    public HospitalImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        HospitalImage data = HospitalImage.builder()
                .name("HOSPITAL" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("hospitals")
                .build();
        HospitalImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String filePath = FOLDER_PATH + "HOSPITAL" + savedFile.getId().toString() + file.getOriginalFilename();

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName("HOSPITAL" + savedFile.getId().toString() + file.getOriginalFilename());

        HospitalImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public Map<String, Object> addNewHospital(HospitalDto hospitalDto) {

        DebugHelper.printData(hospitalDto.toString());

        boolean isSymptomOk = true;
        boolean isDegreeOk = true;
        boolean isDeptOk = departmentRepository.existsById(hospitalDto.getDeptId());

        DebugHelper.printData(hospitalDto.toString());

        Map<String, Object> result = new HashMap<String, Object>();
        if (isDeptOk && isSymptomOk && isDegreeOk) {
            Hospital hospital = new Hospital();
            if (hospitalRepository.existsById(hospitalDto.getId())) {
                result.put("id", hospitalDto.getId());
                result.put("message", "Hospital already exists");
                result.put("isSuccess", false);

                return result;
            }

            hospital.setId(hospitalDto.getId());
            BeanUtils.copyProperties(hospitalDto, hospital);

            hospital.setDept(departmentRepository.findById(hospitalDto.getDeptId())
                    .orElseThrow(() -> new IllegalStateException("department does not exit")));

            Hospital savedHospital = hospitalRepository.save(hospital);
            DebugHelper.printData(savedHospital.toString());
            result.put("id", savedHospital.getId());
            result.put("message", "Hospital added successfully");
            result.put("isSuccess", true);

            return result;

        } else {
            if (!isDeptOk) {
                result.put("id", null);
                result.put("message", "HospitalDepartment does not exist");
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

    public void deleteHospital(Long id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Hospital does not exit");
        }
    }

    @Transactional
    // public void updateHospital(Long id, HospitalDto hospital) {

    public Hospital updateHospital(EditHospitalDto hospital) {

        Hospital newHospital = hospitalRepository.findById(hospital.getId())
                .orElseThrow(() -> new IllegalStateException("Hospital does not exit"));
        DebugHelper.printData(newHospital.toString());

        boolean isDeptOk = true;

        if (isDeptOk) {
            if (hospital.getName() != null && hospital.getName().length() > 0) {
                newHospital.setName(hospital.getName());
            }
            if (hospital.getBangla_name() != null && hospital.getBangla_name().length() > 0) {
                newHospital.setBangla_name(hospital.getBangla_name());
            }
            if (hospital.getAddress() != null && hospital.getAddress().length() > 0) {
                newHospital.setAddress(hospital.getAddress());
            }
            if (hospital.getPhone() != null && hospital.getPhone().length() > 0) {
                newHospital.setPhone(hospital.getPhone());
            }
            if (hospital.getDeptId() != null) {
                HospitalDepartment dept = departmentRepository.findById(hospital.getDeptId())
                        .orElseThrow(() -> new IllegalStateException("HospitalDepartment does not exit"));
                newHospital.setDept(dept);
            }
            System.out.println(newHospital.toString());

            return newHospital;
        }

        return null;

    }

    // private final String FOLDER_PATH =
    // "D:/dev/backend/java/java_backend/src/images/hospitals/";
    private final String FOLDER_PATH = AppConstant.folder_path + "/hospitals/";

    public String uploadImageToFileSystem(Long id, MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + "HOSPITAL" + id + "_" + file.getOriginalFilename();

        Hospital newHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Hospital does not exit"));

        newHospital.setImage_file_path(filePath);
        System.out.println(newHospital);
        System.out.println(filePath);

        file.transferTo(new File(filePath));

        return "Successful";
    }

    public byte[] getImage(Long id) throws IOException {

        Optional<HospitalImage> savedFile = imageRepository.findById(id);

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);
            byte[] image = Files.readAllBytes(new File(filePath).toPath());
            return image;
        } else {
            return null;
        }
    }
}
