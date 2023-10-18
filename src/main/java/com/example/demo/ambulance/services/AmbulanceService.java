package com.example.demo.ambulance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.ambulance.dto.AddAmbulanceDto;
import com.example.demo.ambulance.dto.EditAmbulanceDto;
import com.example.demo.ambulance.entity.Ambulance;
import com.example.demo.ambulance.repositories.AmbulanceRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;

    public List<Ambulance> getAmbulances() {
        return ambulanceRepository.findAll();
    }

    public boolean addNewAmbulance(AddAmbulanceDto ambulanceDto) {
        Optional<Ambulance> existingType = ambulanceRepository.findByName(ambulanceDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        Ambulance newType = new Ambulance();
        BeanUtils.copyProperties(ambulanceDto, newType);

        ambulanceRepository.save(newType);
        return true;

    }

    public void deleteAmbulance(Long id) {

        if (ambulanceRepository.existsById(id)) {
            ambulanceRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Ambulance does not exit");
        }
    }

    @Transactional
    public Ambulance updateAmbulance(Long id, EditAmbulanceDto ambulance) {
        Ambulance newAmbulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ambulance does not exit"));

        System.out.println(newAmbulance);

        if (ambulance.getName() != null && ambulance.getName().length() > 0) {
            Optional<Ambulance> existingAmbulance = ambulanceRepository.findByName(ambulance.getName());

            if (existingAmbulance.isPresent()) {
                return null;
            }
            newAmbulance.setName(ambulance.getName());
        }
        if (ambulance.getAddress() != null && ambulance.getAddress().length() > 0) {
            newAmbulance.setAddress(ambulance.getAddress());
        }
        if (ambulance.getContact() != null && ambulance.getContact().length() > 0) {
            newAmbulance.setContact(ambulance.getContact());
        }
        return newAmbulance;
    }

}
