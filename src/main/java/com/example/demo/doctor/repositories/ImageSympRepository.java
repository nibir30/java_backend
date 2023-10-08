package com.example.demo.doctor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.doctor.entity.SymptomImage;

@Repository
public interface ImageSympRepository extends JpaRepository<SymptomImage, Long> {
    Optional<SymptomImage> findByName(String fileName);

}
