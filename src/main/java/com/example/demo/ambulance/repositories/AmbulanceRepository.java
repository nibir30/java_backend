package com.example.demo.ambulance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.ambulance.entity.Ambulance;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    Optional<Ambulance> findByName(String fileName);

}
