package com.example.demo.coaching.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.coaching.dto.AddCoachingDto;
import com.example.demo.coaching.dto.EditCoachingDto;
import com.example.demo.coaching.entity.Coaching;
import com.example.demo.coaching.repositories.CoachingRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CoachingService {
    private final CoachingRepository coachingRepository;

    public List<Coaching> getCoachings() {
        return coachingRepository.findAll();
    }

    public boolean addNewCoaching(AddCoachingDto coachingDto) {
        Optional<Coaching> existingType = coachingRepository.findByName(coachingDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        Coaching newType = new Coaching();
        BeanUtils.copyProperties(coachingDto, newType);

        coachingRepository.save(newType);
        return true;

    }

    public void deleteCoaching(Long id) {

        if (coachingRepository.existsById(id)) {
            coachingRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Coaching does not exit");
        }
    }

    @Transactional
    public Coaching updateCoaching(Long id, EditCoachingDto coaching) {
        Coaching newCoaching = coachingRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Coaching does not exit"));

        System.out.println(newCoaching);

        if (coaching.getName() != null && coaching.getName().length() > 0) {
            Optional<Coaching> existingCoaching = coachingRepository.findByName(coaching.getName());

            if (existingCoaching.isPresent()) {
                return null;
            }
            newCoaching.setName(coaching.getName());
        }
        if (coaching.getAddress() != null && coaching.getAddress().length() > 0) {
            newCoaching.setAddress(coaching.getAddress());
        }
        if (coaching.getContact() != null && coaching.getContact().length() > 0) {
            newCoaching.setContact(coaching.getContact());
        }
        return newCoaching;
    }

}
