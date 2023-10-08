package com.example.demo.doctor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.doctor.entity.DoctorImage;
import java.util.List;

@Repository
public interface ImageDoctorRepository extends JpaRepository<DoctorImage, Long> {
    Optional<DoctorImage> findByName(String fileName);

    Optional<DoctorImage> findByFilePath(String filePath);

}
