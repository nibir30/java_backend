package com.example.demo.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // @Query("SELECT D FROM Doctor d WHERE d.email = ?1")
    // Optional<Doctor> findDOctorByEmail(String Email);
}
