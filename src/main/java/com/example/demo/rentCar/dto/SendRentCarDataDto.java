package com.example.demo.rentCar.dto;

import java.util.List;

import com.example.demo.rentCar.entity.RentCar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendRentCarDataDto {
    private List<RentCar> listResponse;

}
