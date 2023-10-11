package com.example.demo.doctor.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.dto.SendDeptDataDto;
import com.example.demo.doctor.entity.Department;
import com.example.demo.doctor.entity.DepartmentImage;
import com.example.demo.doctor.services.DepartmentService;

@RestController
@RequestMapping(path = "api/v1/departments")
public class DepartmentController {
    private final DepartmentService service;

    // @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    // @GetMapping
    // public List<Department> getDepartments() {
    // return service.getDepts();
    // }
    @GetMapping
    public SendDeptDataDto getDepartments() {
        SendDeptDataDto department = new SendDeptDataDto(service.getDepts());
        return department;
    }

    @PostMapping
    public Map<String, Object> addNewDept(@RequestBody Department dept) {
        Map<String, Object> result = service.addNewDept(dept);
        return result;
    }

    @PostMapping("/addImage")
    public DepartmentImage addDeptImage(
            @RequestParam("image") MultipartFile file)
            throws NumberFormatException, IOException {
        DepartmentImage image = service.uploadImageToFileSystem(file);
        if (image != null) {
            return image;
        }
        return null;
    }

    @PostMapping("/updateImage")
    public DepartmentImage updateDoctorImage(
            @RequestParam("image") MultipartFile file, @RequestParam("id") String id)

            throws NumberFormatException, IOException {
        DepartmentImage image = service.updateImageFromFileSystem(file, id);
        if (image != null) {
            return image;
        }
        return null;
    }

    @DeleteMapping(path = "{deptId}")
    public void deleteDept(@PathVariable("deptId") Long id) {
        service.deleteDept(id);
    }

    @PostMapping(path = "{deptId}")
    public void updateDept(@PathVariable("deptId") Long id, @RequestBody Department dept) {
        service.updateDept(id, dept);
    }

    @GetMapping("/getImage/{deptId}")
    public ResponseEntity<?> getDepartments(@PathVariable("deptId") Long id) throws IOException {
        byte[] image = service.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }
}
