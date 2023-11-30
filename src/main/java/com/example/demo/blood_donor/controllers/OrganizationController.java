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
import com.example.demo.blood_donor.entity.OrganizationModel;
import com.example.demo.blood_donor.services.OrganizationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/bloodGroups")
@AllArgsConstructor
public class OrganizationController {
    private final OrganizationService service;

    @GetMapping
    public SendGroupDataDto getOrganizations() {
        SendGroupDataDto groups = new SendGroupDataDto(service.getOrganizations());
        return groups;
    }

    @PostMapping
    public Map<String, Object> addNewOrganization(@RequestBody OrganizationModel group) {
        Map<String, Object> result = service.addNewOrganization(group);
        return result;
    }

    @DeleteMapping(path = "{groupId}")
    public void deleteOrganization(@PathVariable("groupId") Long id) {
        service.deleteOrganization(id);
    }

    @PostMapping(path = "/update/{groupId}")
    public void updateOrganization(@PathVariable("groupId") Long id, @RequestBody OrganizationModel group) {
        service.updateOrganization(id, group);
    }

}
