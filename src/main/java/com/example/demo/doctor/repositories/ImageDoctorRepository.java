package com.example.demo.doctor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.doctor.entity.FileData;

@Repository
public interface ImageDoctorRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String fileName);

}
