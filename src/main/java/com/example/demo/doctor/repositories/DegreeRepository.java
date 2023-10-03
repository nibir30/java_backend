package com.example.demo.doctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.doctor.entity.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
    // @Query("SELECT D FROM Doctor d WHERE d.email = ?1")
    // Optional<Doctor> findDOctorByEmail(String Email);
}
