package com.example.demo.doctor.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditDoctorDto {
    private Long id;
    private String name;
    private String phone;
    private Long deptId;
    private List<Long> degreeIds;
    private List<Long> symptomIds;
    private String image_file_path;
}
