package com.example.demo.doctor.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.entity.Department;
import com.example.demo.doctor.entity.DepartmentImage;
import com.example.demo.doctor.repositories.DepartmentRepository;
import com.example.demo.doctor.repositories.ImageDeptRepository;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository deptRepository;
    private final ImageDeptRepository imageRepository;

    public List<Department> getDepts() {
        return deptRepository.findAll();
    }

    private final String FOLDER_PATH = "D:/Dev/Backend/Java/tngl/java_backend/src/images/departments/";

    public DepartmentImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        DepartmentImage data = DepartmentImage.builder()
                .name("DEPARTMENT" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("departments")
                .build();
        DepartmentImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String filePath = FOLDER_PATH + "DEPARTMENT" + savedFile.getId().toString() + file.getOriginalFilename();

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName("DEPARTMENT" + savedFile.getId().toString() + file.getOriginalFilename());

        DepartmentImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        if (final_data != null || file != null) {
            return final_data;
        }
        return null;

    }

    public Map<String, Object> addNewDept(Department dept) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (deptRepository.existsById(dept.getId())) {
            result.put("id", dept.getId());
            result.put("message", "Department already exists");
            result.put("isSuccess", false);

            return result;
        }

        deptRepository.save(dept);
        result.put("id", dept.getId());
        result.put("message", "Department added successfully");
        result.put("isSuccess", true);

        System.out.println(dept);

        return result;

    }

    public DepartmentImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<DepartmentImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            DepartmentImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
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
        Department dept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        if (name != null && name.length() > 0) {
            dept.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            dept.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateDept(Long id, Department dept) {
        Department newDept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        System.out.println(newDept);

        if (dept.getName() != null && dept.getName().length() > 0) {
            newDept.setName(dept.getName());
        }
        if (dept.getBangla_name() != null && dept.getBangla_name().length() > 0) {
            newDept.setBangla_name(dept.getBangla_name());
        }
    }
}
