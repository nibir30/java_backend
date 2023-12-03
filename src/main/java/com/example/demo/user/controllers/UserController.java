package com.example.demo.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helpers.ResponseHandler;
import com.example.demo.helpers.SuccessMessageModel;
import com.example.demo.user.dto.AddUserDto;
import com.example.demo.user.dto.SendUserDataDto;
import com.example.demo.user.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        SendUserDataDto department = new SendUserDataDto(service.getUsers());
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "Here are the users",
                department);
    }

    @PostMapping
    public ResponseEntity<Object> addNewUser(@RequestBody @Valid AddUserDto user) {
        if (service.addNewUser(user)) {
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                    "User added successfully",
                    new SuccessMessageModel("Successfully added an User", true));
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false,
                "User exists already",
                new SuccessMessageModel("User exists already", false));
    }
}
