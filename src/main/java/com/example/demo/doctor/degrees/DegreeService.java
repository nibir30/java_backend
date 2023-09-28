package com.example.demo.doctor.degrees;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class DegreeService {
    private final DegreeRepository degreeRepository;

    public DegreeService(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    public List<Degree> getDegree() {
        return degreeRepository.findAll();
    }

    public void addNewSymptom(Degree degree) {
        degreeRepository.save(degree);
        System.out.println(degree);
    }

    public void deleteSymptom(Long id) {

        if (degreeRepository.existsById(id)) {
            degreeRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Symptom does not exit");
        }
    }

    @Transactional
    public void updateSymptom(Long id, String name, String bangla_name) {
        Degree degree = degreeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        if (name != null && name.length() > 0) {
            degree.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            degree.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateSymptom(Long id, Degree degree) {
        Degree newSymptom = degreeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        System.out.println(newSymptom);

        if (degree.getName() != null && degree.getName().length() > 0) {
            newSymptom.setName(degree.getName());
        }
        if (degree.getBangla_name() != null && degree.getBangla_name().length() > 0) {
            newSymptom.setBangla_name(degree.getBangla_name());
        }
    }
}
