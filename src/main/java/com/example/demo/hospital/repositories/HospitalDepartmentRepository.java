package com.example.demo.hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.hospital.entity.HospitalDepartment;

@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    // @Query("SELECT D FROM Hospital d WHERE d.email = ?1")
    // Optional<Hospital> findDOctorByEmail(String Email);
}
