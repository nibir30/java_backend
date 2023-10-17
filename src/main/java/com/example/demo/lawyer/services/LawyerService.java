package com.example.demo.lawyer.services;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.demo.lawyer.dto.AddLawyerDto;
import com.example.demo.lawyer.dto.EditLawyerDto;
import com.example.demo.lawyer.entity.*;
import com.example.demo.lawyer.repositories.*;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LawyerService {
    private final LawyerRepository lawyerRepository;
    private final LawyerPracticeTypeRepository departmentRepository;
    private final LawyerCourtRepository courtRepository;

    public List<Lawyer> getLawyers() {
        return lawyerRepository.findAll();
    }

    public boolean addNewLawyer(AddLawyerDto lawyerDto) {

        DebugHelper.printData(lawyerDto.toString());

        Lawyer lawyer = new Lawyer();
        BeanUtils.copyProperties(lawyerDto, lawyer);

        lawyer.setPracticeType(departmentRepository.findById(lawyerDto.getPracticeTypeId())
                .orElseThrow(() -> new IllegalStateException("Practice type does not exit")));
        lawyer.setCourt(courtRepository.findById(lawyerDto.getCourtId())
                .orElseThrow(() -> new IllegalStateException("Court does not exit")));

        Lawyer savedLawyer = lawyerRepository.save(lawyer);
        DebugHelper.printData(savedLawyer.toString());

        return true;
    }

    public void deleteLawyer(Long id) {
        if (lawyerRepository.existsById(id)) {
            lawyerRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Lawyer does not exit");
        }
    }

    @Transactional
    public Lawyer updateLawyer(Long id, EditLawyerDto lawyer) {

        Lawyer newLawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Lawyer does not exit"));
        DebugHelper.printData(newLawyer.toString());

        if (lawyer.getName() != null && lawyer.getName().length() > 0) {
            newLawyer.setName(lawyer.getName());
        }
        if (lawyer.getBangla_name() != null && lawyer.getBangla_name().length() > 0) {
            newLawyer.setBangla_name(lawyer.getBangla_name());
        }
        if (lawyer.getPhone() != null && lawyer.getPhone().length() > 0) {
            newLawyer.setPhone(lawyer.getPhone());
        }
        if (lawyer.getPracticeTypeId() != null) {
            LawyerPracticeType practiceType = departmentRepository.findById(lawyer.getPracticeTypeId())
                    .orElseThrow(() -> new IllegalStateException("LawyerPracticeType does not exit"));
            newLawyer.setPracticeType(practiceType);
        }
        if (lawyer.getCourtId() != null) {
            LawyerCourt court = courtRepository.findById(lawyer.getCourtId())
                    .orElseThrow(() -> new IllegalStateException("court does not exit"));
            newLawyer.setCourt(court);
        }
        System.out.println(newLawyer.toString());

        return newLawyer;

    }

}
