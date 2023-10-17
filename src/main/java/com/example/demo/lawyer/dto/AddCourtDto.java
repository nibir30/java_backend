package com.example.demo.lawyer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddCourtDto {
    @NotNull(message = "name should not be null")
    private String name;
    @NotNull(message = "bangla_name should not be null")
    private String bangla_name;
}
