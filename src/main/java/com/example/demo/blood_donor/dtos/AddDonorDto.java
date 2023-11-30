package com.example.demo.blood_donor.dtos;

import java.util.Optional;

import com.example.demo.blood_donor.entity.BloodDonor;
import com.example.demo.blood_donor.repositories.OrganizationRepository;

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

}
