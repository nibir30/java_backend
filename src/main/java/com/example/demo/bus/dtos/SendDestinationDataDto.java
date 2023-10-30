package com.example.demo.bus.dtos;

import java.util.List;

import com.example.demo.bus.entity.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendDestinationDataDto {
    private List<Destination> listResponse;

}
