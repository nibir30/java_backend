package com.example.demo.doctor.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.entity.Symptom;
import com.example.demo.doctor.entity.SymptomImage;
import com.example.demo.doctor.services.SymptomService;

@RestController
@RequestMapping(path = "api/v1/symptoms")
public class SymptomController {
    private final SymptomService service;

    // @Autowired
    public SymptomController(SymptomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Symptom> getSymptoms() {
        return service.getSymptoms();
    }

    @PostMapping("/addImage")
    public SymptomImage addSympImage(
            @RequestParam("image") MultipartFile file)
            throws NumberFormatException, IOException {
        SymptomImage image = service.uploadImageToFileSystem(file);
        if (image != null) {
            return image;
        }
        return null;
    }

    @PostMapping
    public Map<String, Object> addNewSymptom(@RequestBody Symptom symptom) {
        Map<String, Object> result = service.addNewSymptom(symptom);
        return result;
    }

    @DeleteMapping(path = "{symptomId}")
    public void deleteSymptom(@PathVariable("symptomId") Long id) {
        service.deleteSymptom(id);
    }

    @PostMapping(path = "{symptomId}")
    public void updateSymptom(@PathVariable("symptomId") Long id, @RequestBody Symptom symptom) {
        service.updateSymptom(id, symptom);
    }
}
