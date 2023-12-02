package com.example.demo.blood_donor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.blood_donor.dtos.AddDonorDto;
import com.example.demo.blood_donor.entity.BloodDonor;
import com.example.demo.blood_donor.entity.OrganizationModel;
import com.example.demo.blood_donor.repositories.BloodDonorRepository;
import com.example.demo.blood_donor.repositories.OrganizationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BloodDonorService {

    private final BloodDonorRepository repository;
    private final OrganizationRepository organizationRepository;

    public List<BloodDonor> getBloodDonors() {
        return repository.findAll();
    }

    public List<BloodDonor> getBloodDonorsByOrganization(Long id) {
        return repository.findByOrganizationId(id);
    }

    public boolean addBloodDonor(AddDonorDto addDonorDto) {
        Optional<BloodDonor> existingType = repository.findByName(addDonorDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        BloodDonor newType = new BloodDonor();
        BeanUtils.copyProperties(addDonorDto, newType);
        Optional<OrganizationModel> organizationModel = organizationRepository
                .findById(addDonorDto.getOrganizationId());
        if (organizationModel.isPresent()) {
            newType.setOrganization(organizationModel.get());
            repository.save(newType);
            return true;
        }
        return false;
    }

    public void deleteBloodDonor(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalStateException("BloodDonor does not exit");
        }
    }
}
