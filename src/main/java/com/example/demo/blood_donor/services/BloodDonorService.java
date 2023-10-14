package com.example.demo.blood_donor.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.blood_donor.dtos.AddDonorDto;
import com.example.demo.blood_donor.entity.BloodDonor;
import com.example.demo.blood_donor.entity.BloodGroup;
import com.example.demo.blood_donor.repositories.BloodDonorRepository;
import com.example.demo.blood_donor.repositories.BloodGroupRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BloodDonorService {

    private final BloodDonorRepository repository;
    private final BloodGroupRepository groupRepository;

    public List<BloodDonor> getBloodDonors() {
        return repository.findAll();
    }

    public List<BloodDonor> getBloodDonorsNyBloodGroup(Long id) {
        return repository.findByBloodGroupId(id);
    }

    public Map<String, Object> addNewBloodDonor(AddDonorDto bloodDonor) {
        Map<String, Object> result = new HashMap<String, Object>();

        BloodDonor donor = bloodDonor.dtoToDonorEntity(groupRepository);
        if (donor != null) {
            repository.save(donor);
            result.put("id", donor.getId());
            result.put("message", "BloodDonor added successfully");
            result.put("isSuccess", true);
        } else {
            result.put("name", bloodDonor.getName());
            result.put("message", "Something went wrong");
            result.put("isSuccess", false);
        }

        System.out.println(donor);

        return result;

    }

    public void deleteBloodDonor(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalStateException("BloodDonor does not exit");
        }
    }

    @Transactional
    public void updateBloodDonor(Long id, AddDonorDto donorDto) {
        BloodDonor bloodDonor = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("BloodDonor does not exit"));

        if (donorDto.getName() != null && donorDto.getName().length() > 0) {
            bloodDonor.setName(donorDto.getName());
        }

        if (donorDto.getAddress() != null && donorDto.getAddress().length() > 0) {
            bloodDonor.setAddress(donorDto.getAddress());
        }
        if (donorDto.getPhone() != null && donorDto.getPhone().length() > 0) {
            bloodDonor.setPhone(donorDto.getPhone());
        }
        if (donorDto.getGender() != null && donorDto.getGender().length() > 0) {
            bloodDonor.setGender(donorDto.getGender());
        }
        if (donorDto.getBloodGroupID() != null && donorDto.getBloodGroupID() != 0) {
            Optional<BloodGroup> group = groupRepository.findById(donorDto.getBloodGroupID());
            if (group.isPresent()) {
                bloodDonor.setBloodGroup(group.get());
            }
        }
    }

}
