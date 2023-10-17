package com.example.demo.lawyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.lawyer.entity.LawyerPracticeType;

@Repository
public interface LawyerPracticeTypeRepository extends JpaRepository<LawyerPracticeType, Long> {
    // @Query("SELECT D FROM Lawyer d WHERE d.email = ?1")
    // Optional<Lawyer> findDOctorByEmail(String Email);
}
