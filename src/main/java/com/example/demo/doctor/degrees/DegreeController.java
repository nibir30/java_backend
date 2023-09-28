package com.example.demo.doctor.degrees;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/degree")
public class DegreeController {
    private final DegreeService service;

    // @Autowired
    public DegreeController(DegreeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Degree> getDegree() {
        return service.getDegree();
    }

    @PostMapping
    public void addNewSymptom(@RequestBody Degree degree) {
        service.addNewSymptom(degree);
    }

    @DeleteMapping(path = "{degreeId}")
    public void deleteSymptom(@PathVariable("degreeId") Long id) {
        service.deleteSymptom(id);
    }

    // @PutMapping(path = "{degreeId}")
    // public void updateDoctor(@PathVariable("degreeId") Long id,
    //         @RequestParam(required = false) String name,
    //         @RequestParam(required = false) String degree,
    //         @RequestParam(required = false) String degree,
    //         @RequestParam(required = false) String degrees) {
    //     service.updateDoctor(id, name, degree, degree, degrees);
    // }

    @PostMapping(path = "{degreeId}")
    public void updateSymptom(@PathVariable("degreeId") Long id, @RequestBody Degree degree) {
        service.updateSymptom(id, degree);
    }
}
