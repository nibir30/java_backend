package com.example.demo.lawyer.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditLawyerDto {
    private Long id;
    private String name;
    private String bangla_name;
    private String phone;
    private Long practiceTypeId;
    private String address;
    private String image_file_path;
}
