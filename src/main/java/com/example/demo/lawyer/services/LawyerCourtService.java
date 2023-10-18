package com.example.demo.lawyer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.lawyer.dto.AddCourtDto;
import com.example.demo.lawyer.dto.EditCourtDto;
import com.example.demo.lawyer.entity.LawyerCourt;
import com.example.demo.lawyer.repositories.LawyerCourtRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LawyerCourtService {
    private final LawyerCourtRepository courtRepository;

    public List<LawyerCourt> getCourts() {
        return courtRepository.findAll();
    }

    public boolean addNewCourt(AddCourtDto courtDto) {
        Optional<LawyerCourt> existingType = courtRepository.findByName(courtDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        LawyerCourt newType = new LawyerCourt();
        newType.setName(courtDto.getName());
        newType.setBangla_name(courtDto.getBangla_name());

        courtRepository.save(newType);
        return true;

    }

    public void deleteCourt(Long id) {

        if (courtRepository.existsById(id)) {
            courtRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Court does not exit");
        }
    }

    @Transactional
    public boolean updateCourt(Long id, EditCourtDto court) {
        LawyerCourt newCourt = courtRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Court does not exit"));

        Optional<LawyerCourt> existingCourt = courtRepository.findByName(court.getName());

        if (existingCourt.isPresent()) {
            return false;
        }
        System.out.println(newCourt);

        if (court.getName() != null && court.getName().length() > 0) {
            newCourt.setName(court.getName());
        }
        if (court.getBangla_name() != null && court.getBangla_name().length() > 0) {
            newCourt.setBangla_name(court.getBangla_name());
        }

        return true;
    }

}
