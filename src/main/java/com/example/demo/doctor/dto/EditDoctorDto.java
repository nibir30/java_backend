package com.example.demo.doctor.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
public class EditDoctorDto {
    private Long id;
    private String name;
    private String phone;
    private Long deptId;
    private List<Long> degreeIds;
    private List<Long> symptomIds;
    private String image_file_path;
    private boolean homeService;

    public boolean getHomeService() {
        return homeService;
    }

    // public void setHomeService(boolean home) {
    // homeService = home;
    // }

}
