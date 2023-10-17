package com.example.demo.lawyer.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.lawyer.dto.SendPracticeTypeDataDto;
import com.example.demo.lawyer.entity.LawyerPracticeType;
import com.example.demo.lawyer.services.LawyerPracticeTypeService;

@RestController
@RequestMapping(path = "api/v1/lawyers/departments")
public class LawyerPracticeTypeController {
    private final LawyerPracticeTypeService service;

    // @Autowired
    public LawyerPracticeTypeController(LawyerPracticeTypeService service) {
        this.service = service;
    }

    // @GetMapping
    // public List<LawyerPracticeType> getLawyerPracticeTypes() {
    // return service.getPracticeTypes();
    // }
    @GetMapping
    public SendPracticeTypeDataDto getLawyerPracticeTypes() {
        SendPracticeTypeDataDto department = new SendPracticeTypeDataDto(service.getPracticeTypes());
        return department;
    }

    @PostMapping
    public Map<String, Object> addNewLawyerPracticeType(@RequestBody LawyerPracticeType practiceType) {
        Map<String, Object> result = service.addNewPracticeType(practiceType);
        return result;
    }

    @DeleteMapping(path = "{practiceTypeId}")
    public void deleteLawyerPracticeType(@PathVariable("practiceTypeId") Long id) {
        service.deletePracticeType(id);
    }

    @PostMapping(path = "{practiceTypeId}")
    public void updatePracticeType(@PathVariable("practiceTypeId") Long id,
            @RequestBody LawyerPracticeType practiceType) {
        service.updatePracticeType(id, practiceType);
    }

}
