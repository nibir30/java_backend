package com.example.demo.blood_donor.dtos;

import java.util.List;

import com.example.demo.blood_donor.entity.OrganizationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendOrganizationDataDto {
    private List<OrganizationModel> organizations;
}
