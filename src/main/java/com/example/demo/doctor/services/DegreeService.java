package com.example.demo.doctor.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.doctor.entity.Degree;
import com.example.demo.doctor.repositories.DegreeRepository;

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

    public Map<String, Object> addNewDegree(Degree degree) {
        Degree savedDegree = degreeRepository.save(degree);
        Map<String, Object> result = new HashMap<String, Object>();
        if (savedDegree != null) {
            result.put("id", savedDegree.getId());
            result.put("message", "Degree added successfully");
            result.put("success", true);
            return result;
        }
        result.put("message", "Unsuccessful");
        result.put("success", false);
        System.out.println(degree);

        return result;

    }

    public void deleteDegree(Long id) {

        if (degreeRepository.existsById(id)) {
            degreeRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Degree does not exit");
        }
    }

    @Transactional
    public void updateDegree(Long id, String name, String bangla_name) {
        Degree degree = degreeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Degree does not exit"));

        if (name != null && name.length() > 0) {
            degree.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            degree.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateDegree(Long id, Degree degree) {
        Degree newDegree = degreeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Degree does not exit"));

        System.out.println(newDegree);

        if (degree.getName() != null && degree.getName().length() > 0) {
            newDegree.setName(degree.getName());
        }
        if (degree.getBangla_name() != null && degree.getBangla_name().length() > 0) {
            newDegree.setBangla_name(degree.getBangla_name());
        }
    }
}
