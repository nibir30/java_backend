package com.example.demo.blood_donor.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.blood_donor.entity.OrganizationModel;
import com.example.demo.blood_donor.repositories.OrganizationRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository repository;

    public List<OrganizationModel> getOrganizations() {
        return repository.findAll();
    }

    public Map<String, Object> addNewOrganization(OrganizationModel bloodGroup) {
        Map<String, Object> result = new HashMap<String, Object>();

        Optional<OrganizationModel> existingOrganization = repository.findByName(bloodGroup.getName());
        if (existingOrganization.isPresent()) {
            result.put("id", bloodGroup.getId());
            result.put("message", "Organization already exists");
            result.put("success", false);

            return result;
        }

        repository.save(bloodGroup);
        result.put("id", bloodGroup.getId());
        result.put("message", "Organization added successfully");
        result.put("success", true);

        System.out.println(bloodGroup);

        return result;

    }

    public void deleteOrganization(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalStateException("Organization does not exit");
        }
    }

    @Transactional
    public void updateOrganization(Long id, String name) {
        OrganizationModel bloodGroup = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Organization does not exit"));

        if (name != null && name.length() > 0) {
            bloodGroup.setName(name);
        }
    }

    @Transactional
    public void updateOrganization(Long id, OrganizationModel bloodGroup) {
        OrganizationModel newOrganization = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Organization does not exit"));

        System.out.println(newOrganization);

        if (bloodGroup.getName() != null && bloodGroup.getName().length() > 0) {
            newOrganization.setName(bloodGroup.getName());
        }
    }
}
