package com.example.demo.blood_donor.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.blood_donor.entity.BloodGroup;
import com.example.demo.blood_donor.repositories.BloodGroupRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BloodGroupService {
    private final BloodGroupRepository repository;

    public List<BloodGroup> getBloodGroups() {
        return repository.findAll();
    }

    public Map<String, Object> addNewBloodGroup(BloodGroup bloodGroup) {
        Map<String, Object> result = new HashMap<String, Object>();

        Optional<BloodGroup> existingBloodGroup = repository.findByName(bloodGroup.getName());
        if (existingBloodGroup.isPresent()) {
            result.put("id", bloodGroup.getId());
            result.put("message", "BloodGroup already exists");
            result.put("isSuccess", false);

            return result;
        }

        repository.save(bloodGroup);
        result.put("id", bloodGroup.getId());
        result.put("message", "BloodGroup added successfully");
        result.put("isSuccess", true);

        System.out.println(bloodGroup);

        return result;

    }

    public void deleteBloodGroup(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalStateException("BloodGroup does not exit");
        }
    }

    @Transactional
    public void updateBloodGroup(Long id, String name) {
        BloodGroup bloodGroup = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("BloodGroup does not exit"));

        if (name != null && name.length() > 0) {
            bloodGroup.setName(name);
        }
    }

    @Transactional
    public void updateBloodGroup(Long id, BloodGroup bloodGroup) {
        BloodGroup newBloodGroup = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("BloodGroup does not exit"));

        System.out.println(newBloodGroup);

        if (bloodGroup.getName() != null && bloodGroup.getName().length() > 0) {
            newBloodGroup.setName(bloodGroup.getName());
        }
    }
}
