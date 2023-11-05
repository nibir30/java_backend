package com.example.demo.hospital.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HospitalDto {

    @NotEmpty(message = "Hospital name should not be empty")
    private String name;
    @NotEmpty(message = "Bangla name should not be empty")
    private String bangla_name;
    @NotEmpty(message = "Phone should not be empty")
    private String phone;
    @NotEmpty(message = "Type should not be empty")
    private String type;
    private String address;

    private String webLink;
    private String mapLink;

}