package com.example.demo.blood_donor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.blood_donor.entity.OrganizationImage;

@Repository
public interface ImageOranizationRepository extends JpaRepository<OrganizationImage, Long> {
    Optional<OrganizationImage> findByName(String fileName);

    Optional<OrganizationImage> findByFilePath(String filePath);

}
