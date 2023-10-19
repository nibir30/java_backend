package com.example.demo.helpers;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SuccessMessageModel {
    private String message;
    private boolean success;

}
