package com.example.demo.hospital.controllers;

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

import com.example.demo.hospital.dto.SendDeptDataDto;
import com.example.demo.hospital.entity.HospitalDepartment;
import com.example.demo.hospital.entity.HospitalDepartmentImage;
import com.example.demo.hospital.services.HospitalDepartmentService;

@RestController
@RequestMapping(path = "api/v1/hospitals/departments")
public class HospitalDepartmentController {
    private final HospitalDepartmentService service;

    // @Autowired
    public HospitalDepartmentController(HospitalDepartmentService service) {
        this.service = service;
    }

    // @GetMapping
    // public List<HospitalDepartment> getHospitalDepartments() {
    // return service.getDepts();
    // }
    @GetMapping
    public SendDeptDataDto getHospitalDepartments() {
        SendDeptDataDto department = new SendDeptDataDto(service.getDepts());
        return department;
    }

    @PostMapping
    public Map<String, Object> addNewHospitalDept(@RequestBody HospitalDepartment dept) {
        Map<String, Object> result = service.addNewDept(dept);
        return result;
    }

    @PostMapping("/addImage")
    public HospitalDepartmentImage addDeptImage(
            @RequestParam("image") MultipartFile file)
            throws NumberFormatException, IOException {
        HospitalDepartmentImage image = service.uploadImageToFileSystem(file);
        if (image != null) {
            return image;
        }
        return null;
    }

    @PostMapping("/updateImage")
    public HospitalDepartmentImage updateHospitalImage(
            @RequestParam("image") MultipartFile file, @RequestParam("id") String id)

            throws NumberFormatException, IOException {
        HospitalDepartmentImage image = service.updateImageFromFileSystem(file, id);
        if (image != null) {
            return image;
        }
        return null;
    }

    @DeleteMapping(path = "{deptId}")
    public void deleteHospitalDept(@PathVariable("deptId") Long id) {
        service.deleteDept(id);
    }

    @PostMapping(path = "{deptId}")
    public void updateDept(@PathVariable("deptId") Long id, @RequestBody HospitalDepartment dept) {
        service.updateDept(id, dept);
    }

    @GetMapping("/getImage/{deptId}")
    public ResponseEntity<?> getHospitalDepartments(@PathVariable("deptId") Long id) throws IOException {
        byte[] image = service.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }
}
