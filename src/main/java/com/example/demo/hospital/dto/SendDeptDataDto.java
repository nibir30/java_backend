package com.example.demo.hospital.dto;

import java.util.List;

import com.example.demo.hospital.entity.HospitalDepartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendDeptDataDto {
    private List<HospitalDepartment> departments;

}
