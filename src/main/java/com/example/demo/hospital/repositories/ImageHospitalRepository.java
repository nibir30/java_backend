package com.example.demo.hospital.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.hospital.entity.HospitalImage;

@Repository
public interface ImageHospitalRepository extends JpaRepository<HospitalImage, Long> {
    Optional<HospitalImage> findByName(String fileName);

    Optional<HospitalImage> findByFilePath(String filePath);

}
