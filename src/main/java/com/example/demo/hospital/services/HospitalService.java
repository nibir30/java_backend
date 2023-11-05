package com.example.demo.hospital.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import com.example.demo.hospital.dto.EditHospitalDto;
import com.example.demo.hospital.dto.HospitalDto;
import com.example.demo.hospital.entity.Hospital;
import com.example.demo.hospital.repositories.HospitalRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    public boolean addNewHospital(HospitalDto hospitalDto) {
        Optional<Hospital> existingType = hospitalRepository.findByName(hospitalDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        Hospital newType = new Hospital();
        BeanUtils.copyProperties(hospitalDto, newType);

        hospitalRepository.save(newType);
        return true;

    }

    public void deleteHospital(Long id) {

        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Hospital does not exit");
        }
    }

    @Transactional
    public Hospital updateHospital(Long id, EditHospitalDto hospital) {
        Hospital newHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Hospital does not exit"));

        System.out.println(newHospital);

        BeanUtils.copyProperties(hospital, newHospital, getNullPropertyNames(hospital));

        return newHospital;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
