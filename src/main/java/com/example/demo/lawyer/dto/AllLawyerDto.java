package com.example.demo.lawyer.dto;

import java.util.List;

import com.example.demo.lawyer.entity.Lawyer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class AllLawyerDto {
    private List<Lawyer> lawyers;
}
