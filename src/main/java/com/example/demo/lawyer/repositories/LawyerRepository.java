package com.example.demo.lawyer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.lawyer.entity.Lawyer;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    // @Query("SELECT D FROM Lawyer d WHERE d.email = ?1")
    // Optional<Lawyer> findDOctorByEmail(String Email);
    Optional<Lawyer> findByName(String fileName);
}
