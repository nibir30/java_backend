package com.example.demo.doctor.dto;

import java.util.List;

import com.example.demo.doctor.entity.Doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendDoctorDataDto {
    private List<Doctor> data;

}
