package com.example.demo.doctor.symptom;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public List<Symptom> getSymptoms() {
        return symptomRepository.findAll();
    }

    public void addNewSymptom(Symptom symptom) {
        symptomRepository.save(symptom);
        System.out.println(symptom);
    }

    public void deleteSymptom(Long id) {

        if (symptomRepository.existsById(id)) {
            symptomRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Symptom does not exit");
        }
    }

    @Transactional
    public void updateSymptom(Long id, String name, String bangla_name) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        if (name != null && name.length() > 0) {
            symptom.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            symptom.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateSymptom(Long id, Symptom symptom) {
        Symptom newSymptom = symptomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        System.out.println(newSymptom);

        if (symptom.getName() != null && symptom.getName().length() > 0) {
            newSymptom.setName(symptom.getName());
        }
        if (symptom.getBangla_name() != null && symptom.getBangla_name().length() > 0) {
            newSymptom.setBangla_name(symptom.getBangla_name());
        }
    }
}
