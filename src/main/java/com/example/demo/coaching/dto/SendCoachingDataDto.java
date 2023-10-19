package com.example.demo.coaching.dto;

import java.util.List;

import com.example.demo.coaching.entity.Coaching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendCoachingDataDto {
    private List<Coaching> listResponse;

}
