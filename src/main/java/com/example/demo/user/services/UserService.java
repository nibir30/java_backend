package com.example.demo.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.user.dto.AddUserDto;
import com.example.demo.user.entity.UserModel;
import com.example.demo.user.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public boolean addNewUser(AddUserDto userDto) {
        Optional<UserModel> existingType = userRepository.findByPhone(userDto.getPhone());

        if (existingType.isPresent()) {
            return true;
        }
        UserModel newType = new UserModel();
        newType.setPhone(userDto.getPhone());

        userRepository.save(newType);
        return true;
    }

}
