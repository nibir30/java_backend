package com.example.demo.bus.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditBusDto {

    private String name;
    private String bangla_name;
    private Long destinationId;
    private String routeTypeString;
    private String type;
    private String fee;
    private String time;
    private String phone;
    private String address;
    private String ticketLink;

}