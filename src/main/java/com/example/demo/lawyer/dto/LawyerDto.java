package com.example.demo.lawyer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LawyerDto {

    @NotNull(message = "Id should not be null")
    private Long id;
    @NotEmpty(message = "Lawyer name should not be empty")
    private String name;
    @NotEmpty(message = "Bangla name should not be empty")
    private String bangla_name;
    @NotEmpty(message = "Phone should not be empty")
    private String phone;
    @NotEmpty(message = "LawyerPracticeType should not be empty")
    private Long practiceTypeId;
    private String address;
    private String image_file_path;

}