package com.example.demo.bloodBank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.bloodBank.dto.AddBloodBankDto;
import com.example.demo.bloodBank.dto.EditBloodBankDto;
import com.example.demo.bloodBank.entity.BloodBank;
import com.example.demo.bloodBank.repositories.BloodBankRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BloodBankService {
    private final BloodBankRepository bloodBankRepository;

    public List<BloodBank> getBloodBanks() {
        return bloodBankRepository.findAll();
    }

    public boolean addNewBloodBank(AddBloodBankDto bloodBankDto) {
        Optional<BloodBank> existingType = bloodBankRepository.findByName(bloodBankDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        BloodBank newType = new BloodBank();
        BeanUtils.copyProperties(bloodBankDto, newType);

        bloodBankRepository.save(newType);
        return true;

    }

    public void deleteBloodBank(Long id) {

        if (bloodBankRepository.existsById(id)) {
            bloodBankRepository.deleteById(id);
        } else {
            throw new IllegalStateException("BloodBank does not exit");
        }
    }

    @Transactional
    public BloodBank updateBloodBank(Long id, EditBloodBankDto bloodBank) {
        BloodBank newBloodBank = bloodBankRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("BloodBank does not exit"));

        System.out.println(newBloodBank);

        if (bloodBank.getName() != null && bloodBank.getName().length() > 0) {
            Optional<BloodBank> existingBloodBank = bloodBankRepository.findByName(bloodBank.getName());

            if (existingBloodBank.isPresent()) {
                return null;
            }
            newBloodBank.setName(bloodBank.getName());
        }
        if (bloodBank.getAddress() != null && bloodBank.getAddress().length() > 0) {
            newBloodBank.setAddress(bloodBank.getAddress());
        }
        if (bloodBank.getContact() != null && bloodBank.getContact().length() > 0) {
            newBloodBank.setContact(bloodBank.getContact());
        }
        return newBloodBank;
    }

}
