package com.example.demo.hospital.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditHospitalDto {
    private String name;
    private String bangla_name;
    private String phone;
    private String address;
    private String type;
    private String webLink;
    private String mapLink;
}
