package com.example.demo.hospital.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.hospital.entity.HospitalDepartmentImage;

@Repository
public interface ImageHospitalDeptRepository extends JpaRepository<HospitalDepartmentImage, Long> {
    Optional<HospitalDepartmentImage> findByName(String fileName);

}
