package com.example.demo.hospital.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.hospital.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    // @Query("SELECT D FROM Hospital d WHERE d.email = ?1")
    // Optional<Hospital> findDOctorByEmail(String Email);
    Optional<Hospital> findByName(String fileName);

}
