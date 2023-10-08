package com.example.demo.doctor.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DoctorDto {

    private Long id;
    @NotEmpty(message = "Project name should not be empty")
    private String name;
    @NotEmpty(message = "Phone should not be empty")
    private String phone;
    @NotEmpty(message = "Department should not be empty")
    private Long deptId;
    @NotEmpty(message = "Degrees should not be empty")
    private List<Long> degreeIds;
    private List<Long> symptomIds;
    private String image_file_path;

}