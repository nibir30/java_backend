package com.example.demo.blood_donor.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddDonorDto {
    private String name;
    private String bangla_name;

    private String chairman_name;
    private String chairman_phone;
    private String chairman_address;

    private String generalSecretary_name;
    private String generalSecretary_phone;
    private String generalSecretary_address;
    private Long organizationId;

}
