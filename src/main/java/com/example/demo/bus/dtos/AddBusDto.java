package com.example.demo.bus.dtos;

import com.example.demo.bus.enums.FromToEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddBusDto {

    @NotNull(message = "Name should not be null")
    private String name;

    @NotNull(message = "Name should not be null")
    private String destinationString;

    @NotNull(message = "fromOrTo should not be null")
    private FromToEnum fromOrTo;

    @NotNull(message = "type should not be null")
    private String type;

    @NotNull(message = "fee should not be null")
    private String fee;

    @NotNull(message = "time should not be null")
    private String time;

    @NotNull(message = "phone should not be null")
    private String phone;

    private String address;

    private String ticketLink;

}