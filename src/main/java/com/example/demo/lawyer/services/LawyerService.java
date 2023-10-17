package com.example.demo.lawyer.services;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.demo.lawyer.dto.LawyerDto;
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

    public List<Lawyer> getLawyers() {
        return lawyerRepository.findAll();
    }

    public Map<String, Object> addNewLawyer(LawyerDto lawyerDto) {

        DebugHelper.printData(lawyerDto.toString());

        boolean isSymptomOk = true;
        boolean isDegreeOk = true;
        boolean isPracticeTypeOk = departmentRepository.existsById(lawyerDto.getPracticeTypeId());

        DebugHelper.printData(lawyerDto.toString());

        Map<String, Object> result = new HashMap<String, Object>();
        if (isPracticeTypeOk && isSymptomOk && isDegreeOk) {
            Lawyer lawyer = new Lawyer();
            if (lawyerRepository.existsById(lawyerDto.getId())) {
                result.put("id", lawyerDto.getId());
                result.put("message", "Lawyer already exists");
                result.put("isSuccess", false);

                return result;
            }

            lawyer.setId(lawyerDto.getId());
            BeanUtils.copyProperties(lawyerDto, lawyer);

            lawyer.setPracticeType(departmentRepository.findById(lawyerDto.getPracticeTypeId())
                    .orElseThrow(() -> new IllegalStateException("department does not exit")));

            Lawyer savedLawyer = lawyerRepository.save(lawyer);
            DebugHelper.printData(savedLawyer.toString());
            result.put("id", savedLawyer.getId());
            result.put("message", "Lawyer added successfully");
            result.put("isSuccess", true);

            return result;

        } else {
            if (!isPracticeTypeOk) {
                result.put("id", null);
                result.put("message", "LawyerPracticeType does not exist");
                result.put("isSuccess", false);

                return result;
            }
            if (!isSymptomOk) {
                result.put("id", null);
                result.put("message", "Symptom does not exist");
                result.put("isSuccess", false);

                return result;
            }
            if (!isDegreeOk) {
                result.put("id", null);
                result.put("message", "Degree does not exist");
                result.put("isSuccess", false);

                return result;
            }
        }
        result.put("id", null);
        result.put("message", "Error");
        result.put("isSuccess", false);

        return result;
    }

    public void deleteLawyer(Long id) {
        if (lawyerRepository.existsById(id)) {
            lawyerRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Lawyer does not exit");
        }
    }

    @Transactional
    public Lawyer updateLawyer(EditLawyerDto lawyer) {

        Lawyer newLawyer = lawyerRepository.findById(lawyer.getId())
                .orElseThrow(() -> new IllegalStateException("Lawyer does not exit"));
        DebugHelper.printData(newLawyer.toString());

        boolean isPracticeTypeOk = true;

        if (isPracticeTypeOk) {
            if (lawyer.getName() != null && lawyer.getName().length() > 0) {
                newLawyer.setName(lawyer.getName());
            }
            if (lawyer.getBangla_name() != null && lawyer.getBangla_name().length() > 0) {
                newLawyer.setBangla_name(lawyer.getBangla_name());
            }
            if (lawyer.getAddress() != null && lawyer.getAddress().length() > 0) {
                newLawyer.setAddress(lawyer.getAddress());
            }
            if (lawyer.getPhone() != null && lawyer.getPhone().length() > 0) {
                newLawyer.setPhone(lawyer.getPhone());
            }
            if (lawyer.getPracticeTypeId() != null) {
                LawyerPracticeType practiceType = departmentRepository.findById(lawyer.getPracticeTypeId())
                        .orElseThrow(() -> new IllegalStateException("LawyerPracticeType does not exit"));
                newLawyer.setPracticeType(practiceType);
            }
            System.out.println(newLawyer.toString());

            return newLawyer;
        }

        return null;

    }

}
