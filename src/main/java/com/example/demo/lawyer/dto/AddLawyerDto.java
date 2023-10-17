package com.example.demo.lawyer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddLawyerDto {

    @NotBlank(message = "Lawyer name should not be empty")
    private String name;
    @NotEmpty(message = "Bangla name should not be empty")
    private String bangla_name;
    @NotBlank(message = "Phone should not be empty")
    private String phone;
    @NotBlank(message = "LawyerPracticeType should not be empty")
    private Long practiceTypeId;
    @NotBlank(message = "CourtName should not be empty")
    private Long courtId;
}