package com.example.demo.bus.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GetAllBusDto {
    private String routeTypeString;
    private Long destinationId;
}
