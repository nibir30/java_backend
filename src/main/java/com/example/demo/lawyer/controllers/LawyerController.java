package com.example.demo.lawyer.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.lawyer.dto.LawyerDto;
import com.example.demo.lawyer.dto.AllLawyerDto;
import com.example.demo.lawyer.dto.EditLawyerDto;
import com.example.demo.lawyer.services.LawyerService;
import com.example.demo.helpers.DebugHelper;

@RestController
@RequestMapping(path = "api/v1/lawyers")
public class LawyerController {
    private final LawyerService service;

    public LawyerController(LawyerService service) {
        this.service = service;
    }

    @GetMapping
    public AllLawyerDto getLawyers() {
        AllLawyerDto lawyerDto = new AllLawyerDto(service.getLawyers());
        return lawyerDto;
    }

    @PostMapping()
    public Map<String, Object> addNewLawyer(@RequestBody LawyerDto lawyerDto)
            throws NumberFormatException, IOException {

        Map<String, Object> result = service.addNewLawyer(lawyerDto);

        // return ResponseEntity.ok(result);
        return result;

    }

    @DeleteMapping(path = "{lawyerId}")
    public void deleteLawyer(@PathVariable("lawyerId") Long id) {
        service.deleteLawyer(id);
    }

    @PostMapping(path = "/update")
    // @PostMapping(path = "/{lawyerId}")

    public void updateLawyer(
            // @PathVariable("lawyerId") Long id,
            @RequestBody EditLawyerDto lawyer) {
        DebugHelper.printData(lawyer.toString());
        service.updateLawyer(lawyer);
        // service.updateLawyer(id, lawyer);

    }

}
