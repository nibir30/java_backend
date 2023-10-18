package com.example.demo.lawyer.dto;

import java.util.List;

import com.example.demo.lawyer.entity.LawyerPracticeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendPracticeTypeDataDto {
    private List<LawyerPracticeType> practiceTypes;

}
