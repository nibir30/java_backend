package com.example.demo.doctor.symptom;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public void addNewSymptom(@RequestBody Symptom symptom) {
        service.addNewSymptom(symptom);
    }

    @DeleteMapping(path = "{symptomId}")
    public void deleteSymptom(@PathVariable("symptomId") Long id) {
        service.deleteSymptom(id);
    }

    // @PutMapping(path = "{symptomId}")
    // public void updateDoctor(@PathVariable("symptomId") Long id,
    //         @RequestParam(required = false) String name,
    //         @RequestParam(required = false) String symptom,
    //         @RequestParam(required = false) String symptoms,
    //         @RequestParam(required = false) String degrees) {
    //     service.updateDoctor(id, name, symptom, symptoms, degrees);
    // }

    @PostMapping(path = "{symptomId}")
    public void updateSymptom(@PathVariable("symptomId") Long id, @RequestBody Symptom symptom) {
        service.updateSymptom(id, symptom);
    }
}
