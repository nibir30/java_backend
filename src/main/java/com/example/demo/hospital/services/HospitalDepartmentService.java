package com.example.demo.hospital.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.hospital.entity.HospitalDepartment;
import com.example.demo.hospital.entity.HospitalDepartmentImage;
import com.example.demo.hospital.repositories.HospitalDepartmentRepository;
import com.example.demo.hospital.repositories.ImageHospitalDeptRepository;
import com.example.demo.helpers.AppConstant;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HospitalDepartmentService {
    private final HospitalDepartmentRepository deptRepository;
    private final ImageHospitalDeptRepository imageRepository;

    public List<HospitalDepartment> getDepts() {
        return deptRepository.findAll();
    }

    public Map<String, Object> addNewDept(HospitalDepartment dept) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (deptRepository.existsById(dept.getId())) {
            result.put("id", dept.getId());
            result.put("message", "HospitalDepartment already exists");
            result.put("isSuccess", false);

            return result;
        }

        deptRepository.save(dept);
        result.put("id", dept.getId());
        result.put("message", "HospitalDepartment added successfully");
        result.put("isSuccess", true);

        System.out.println(dept);

        return result;

    }

    public void deleteDept(Long id) {

        if (deptRepository.existsById(id)) {
            deptRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Dept does not exit");
        }
    }

    @Transactional
    public void updateDept(Long id, String name, String bangla_name) {
        HospitalDepartment dept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        if (name != null && name.length() > 0) {
            dept.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            dept.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateDept(Long id, HospitalDepartment dept) {
        HospitalDepartment newDept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        System.out.println(newDept);

        if (dept.getName() != null && dept.getName().length() > 0) {
            newDept.setName(dept.getName());
        }
        if (dept.getBangla_name() != null && dept.getBangla_name().length() > 0) {
            newDept.setBangla_name(dept.getBangla_name());
        }
    }

    private final String FOLDER_PATH = AppConstant.folder_path + "/hospitalDepts/";

    public HospitalDepartmentImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        HospitalDepartmentImage data = HospitalDepartmentImage.builder()
                .name("DEPARTMENT" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("hospitalDepts")
                .build();
        HospitalDepartmentImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String name = "";
        if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")) {
            DebugHelper.printData("Paisiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            name = "DEPARTMENT" + savedFile.getId().toString() + "_" + file.getOriginalFilename();
        } else {
            DebugHelper.printData("NAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            name = "DEPARTMENT" + savedFile.getId().toString() + "_" + file.getOriginalFilename() + ".png";
        }
        String filePath = FOLDER_PATH + name;

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName(name);

        HospitalDepartmentImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public HospitalDepartmentImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<HospitalDepartmentImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            HospitalDepartmentImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
    }

    public byte[] getImage(Long id) throws IOException {

        Optional<HospitalDepartmentImage> savedFile = imageRepository.findById(id);

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
