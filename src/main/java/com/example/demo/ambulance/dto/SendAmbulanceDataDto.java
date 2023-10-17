package com.example.demo.ambulance.dto;

import java.util.List;

import com.example.demo.ambulance.entity.Ambulance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendAmbulanceDataDto {
    private List<Ambulance> ambulances;

}
