package com.example.demo.blood_donor.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.blood_donor.dtos.SendGroupDataDto;
import com.example.demo.blood_donor.entity.BloodGroup;
import com.example.demo.blood_donor.services.BloodGroupService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/bloodGroups")
@AllArgsConstructor
public class BloodGroupController {
    private final BloodGroupService service;

    @GetMapping
    public SendGroupDataDto getBloodGroups() {
        SendGroupDataDto groups = new SendGroupDataDto(service.getBloodGroups());
        return groups;
    }

    @PostMapping
    public Map<String, Object> addNewBloodGroup(@RequestBody BloodGroup group) {
        Map<String, Object> result = service.addNewBloodGroup(group);
        return result;
    }

    @DeleteMapping(path = "{groupId}")
    public void deleteBloodGroup(@PathVariable("groupId") Long id) {
        service.deleteBloodGroup(id);
    }

    @PostMapping(path = "{groupId}")
    public void updateBloodGroup(@PathVariable("groupId") Long id, @RequestBody BloodGroup group) {
        service.updateBloodGroup(id, group);
    }

}
