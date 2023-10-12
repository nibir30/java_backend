package com.example.demo.blood_donor.dtos;

import java.util.Optional;

import com.example.demo.blood_donor.entity.BloodDonor;
import com.example.demo.blood_donor.entity.BloodGroup;
import com.example.demo.blood_donor.repositories.BloodGroupRepository;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddDonorDto {
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "phone should not be empty")
    private String phone;
    @NotEmpty(message = "address should not be empty")
    private String address;
    @NotEmpty(message = "gender should not be empty")
    private String gender;
    @NotEmpty(message = "bloodGroupID should not be empty")
    private Long bloodGroupID;

    public BloodDonor dtoToDonorEntity(BloodGroupRepository repository) {
        BloodDonor donor = new BloodDonor();
        donor.setName(name);
        donor.setAddress(address);
        donor.setGender(gender);
        donor.setPhone(phone);

        Optional<BloodGroup> group = repository.findById(bloodGroupID);
        if (group.isPresent()) {
            donor.setBloodGroup(group.get());
        } else {
            return null;

        }
        return donor;

    }
}
