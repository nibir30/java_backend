package com.example.demo.doctor.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DoctorDto {

    @NotNull(message = "Id should not be null")
    private Long id;
    @NotEmpty(message = "Project name should not be empty")
    private String name;
    @NotEmpty(message = "Phone should not be empty")
    private String phone;
    @NotEmpty(message = "Service type should not be empty")
    private boolean homeService;
    @NotEmpty(message = "Department should not be empty")
    private Long deptId;
    @NotEmpty(message = "Degrees should not be empty")
    private List<Long> degreeIds;
    private List<Long> symptomIds;
    private String image_file_path;

}