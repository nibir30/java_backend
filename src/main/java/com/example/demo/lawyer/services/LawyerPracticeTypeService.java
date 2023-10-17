package com.example.demo.lawyer.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.example.demo.lawyer.entity.LawyerPracticeType;
import com.example.demo.lawyer.repositories.LawyerPracticeTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LawyerPracticeTypeService {
    private final LawyerPracticeTypeRepository practiceTypeRepository;

    public List<LawyerPracticeType> getPracticeTypes() {
        return practiceTypeRepository.findAll();
    }

    public Map<String, Object> addNewPracticeType(LawyerPracticeType practiceType) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (practiceTypeRepository.existsById(practiceType.getId())) {
            result.put("id", practiceType.getId());
            result.put("message", "LawyerPracticeType already exists");
            result.put("isSuccess", false);

            return result;
        }

        practiceTypeRepository.save(practiceType);
        result.put("id", practiceType.getId());
        result.put("message", "LawyerPracticeType added successfully");
        result.put("isSuccess", true);

        System.out.println(practiceType);

        return result;

    }

    public void deletePracticeType(Long id) {

        if (practiceTypeRepository.existsById(id)) {
            practiceTypeRepository.deleteById(id);
        } else {
            throw new IllegalStateException("PracticeType does not exit");
        }
    }

    @Transactional
    public void updatePracticeType(Long id, String name, String bangla_name) {
        LawyerPracticeType practiceType = practiceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("PracticeType does not exit"));

        if (name != null && name.length() > 0) {
            practiceType.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            practiceType.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updatePracticeType(Long id, LawyerPracticeType practiceType) {
        LawyerPracticeType newPracticeType = practiceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("PracticeType does not exit"));

        System.out.println(newPracticeType);

        if (practiceType.getName() != null && practiceType.getName().length() > 0) {
            newPracticeType.setName(practiceType.getName());
        }
        if (practiceType.getBangla_name() != null && practiceType.getBangla_name().length() > 0) {
            newPracticeType.setBangla_name(practiceType.getBangla_name());
        }
    }

}
