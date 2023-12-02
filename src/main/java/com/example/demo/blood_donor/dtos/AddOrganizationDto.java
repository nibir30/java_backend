package com.example.demo.blood_donor.dtos;

// import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddOrganizationDto {
    // @NotEmpty(message = "Id should not be empty")
    private Long id;
    // @NotEmpty(message = "Name should not be empty")
    private String name;
    private String bangla_name;
    private String image_file_path;

}
