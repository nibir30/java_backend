package com.example.demo.user.dto;

import java.util.List;

import com.example.demo.user.entity.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendUserDataDto {
    private List<UserModel> users;

}
