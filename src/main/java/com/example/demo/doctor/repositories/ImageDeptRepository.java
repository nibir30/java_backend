package com.example.demo.doctor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.doctor.entity.DepartmentImage;

@Repository
public interface ImageDeptRepository extends JpaRepository<DepartmentImage, Long> {
    Optional<DepartmentImage> findByName(String fileName);

}
