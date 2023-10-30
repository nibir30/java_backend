package com.example.demo.bus.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddBusDto {

    @NotBlank(message = "Lawyer name should not be empty")
    private String name;
    @NotEmpty(message = "Bangla name should not be empty")
    private String bangla_name;
    // @NotEmpty(message = "Destination should not be empty")
    private Long destinationId;
    @NotEmpty(message = "Bangla name should not be empty")
    private String fromOrTo;
    @NotEmpty(message = "type should not be empty")
    private String type;
    @NotEmpty(message = "fee should not be empty")
    private String fee;
    @NotEmpty(message = "time should not be empty")
    private String time;
    @NotEmpty(message = "phone should not be empty")
    private String phone;
    @NotEmpty(message = "address should not be empty")
    private String address;
    private String ticketLink;

}