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

import com.example.demo.hospital.dto.HospitalDto;
import com.example.demo.hospital.dto.AllHospitalDto;
import com.example.demo.hospital.dto.EditHospitalDto;
import com.example.demo.hospital.entity.HospitalImage;
import com.example.demo.hospital.services.HospitalService;
import com.example.demo.helpers.DebugHelper;

@RestController
@RequestMapping(path = "api/v1/hospitals")
public class HospitalController {
    private final HospitalService service;

    public HospitalController(HospitalService service) {
        this.service = service;
    }

    @GetMapping
    public AllHospitalDto getHospitals() {
        AllHospitalDto hospitalDto = new AllHospitalDto(service.getHospitals());
        return hospitalDto;
    }

    @PostMapping()
    public Map<String, Object> addNewHospital(@RequestBody HospitalDto hospitalDto)
            throws NumberFormatException, IOException {

        Map<String, Object> result = service.addNewHospital(hospitalDto);

        // return ResponseEntity.ok(result);
        return result;

    }

    @PostMapping("/addImage")
    public HospitalImage addHospitalImage(
            @RequestParam("image") MultipartFile file)

            throws NumberFormatException, IOException {
        HospitalImage image = service.uploadImageToFileSystem(file);

        if (image != null) {
            return image;
        }
        return null;

    }

    @PostMapping("/updateImage")
    public HospitalImage updateHospitalImage(
            @RequestParam("image") MultipartFile file, @RequestParam("id") String id)

            throws NumberFormatException, IOException {
        HospitalImage image = service.updateImageFromFileSystem(file, id);
        if (image != null) {
            return image;

        }
        return null;
    }

    @DeleteMapping(path = "{hospitalId}")
    public void deleteHospital(@PathVariable("hospitalId") Long id) {
        service.deleteHospital(id);
    }

    @PostMapping(path = "/update")
    // @PostMapping(path = "/{hospitalId}")

    public void updateHospital(
            // @PathVariable("hospitalId") Long id,
            @RequestBody EditHospitalDto hospital) {
        DebugHelper.printData(hospital.toString());
        service.updateHospital(hospital);
        // service.updateHospital(id, hospital);

    }

    @GetMapping("/getImage/{hospitalId}")
    public ResponseEntity<?> getHospitalDepartments(@PathVariable("hospitalId") Long id) throws IOException {
        byte[] image = service.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }
}
