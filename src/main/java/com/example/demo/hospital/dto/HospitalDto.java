package com.example.demo.hospital.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HospitalDto {

    @NotNull(message = "Id should not be null")
    private Long id;
    @NotEmpty(message = "Hospital name should not be empty")
    private String name;
    @NotEmpty(message = "Bangla name should not be empty")
    private String bangla_name;
    @NotEmpty(message = "Phone should not be empty")
    private String phone;
    @NotEmpty(message = "HospitalDepartment should not be empty")
    private Long deptId;
    private String address;
    private String image_file_path;

}