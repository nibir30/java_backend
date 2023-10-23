package com.example.demo.rentCar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.rentCar.dto.AddRentCarDto;
import com.example.demo.rentCar.dto.EditRentCarDto;
import com.example.demo.rentCar.entity.RentCar;
import com.example.demo.rentCar.repositories.RentCarRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RentCarService {
    private final RentCarRepository rentCarRepository;

    public List<RentCar> getRentCars() {
        return rentCarRepository.findAll();
    }

    public boolean addNewRentCar(AddRentCarDto rentCarDto) {
        Optional<RentCar> existingType = rentCarRepository.findByName(rentCarDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        RentCar newType = new RentCar();
        BeanUtils.copyProperties(rentCarDto, newType);

        rentCarRepository.save(newType);
        return true;

    }

    public void deleteRentCar(Long id) {

        if (rentCarRepository.existsById(id)) {
            rentCarRepository.deleteById(id);
        } else {
            throw new IllegalStateException("RentCar does not exit");
        }
    }

    @Transactional
    public RentCar updateRentCar(Long id, EditRentCarDto rentCar) {
        RentCar newRentCar = rentCarRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("RentCar does not exit"));

        System.out.println(newRentCar);

        if (rentCar.getName() != null && rentCar.getName().length() > 0) {
            Optional<RentCar> existingRentCar = rentCarRepository.findByName(rentCar.getName());

            if (existingRentCar.isPresent()) {
                return null;
            }
            newRentCar.setName(rentCar.getName());
        }
        if (rentCar.getAddress() != null && rentCar.getAddress().length() > 0) {
            newRentCar.setAddress(rentCar.getAddress());
        }
        if (rentCar.getContact() != null && rentCar.getContact().length() > 0) {
            newRentCar.setContact(rentCar.getContact());
        }
        return newRentCar;
    }

}
