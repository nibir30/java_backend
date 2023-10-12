package com.example.demo.hospital.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditHospitalDto {
    private Long id;
    private String name;
    private String bangla_name;
    private String phone;
    private Long deptId;
    private String address;
    private String image_file_path;
}
