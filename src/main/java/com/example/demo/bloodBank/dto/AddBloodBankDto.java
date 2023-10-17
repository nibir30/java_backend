package com.example.demo.bloodBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddBloodBankDto {
    @NotNull(message = "name should not be null")
    private String name;
    @NotBlank(message = "bangla_name should not be null")
    private String contact;
    @NotBlank(message = "bangla_name should not be null")
    private String address;
}
