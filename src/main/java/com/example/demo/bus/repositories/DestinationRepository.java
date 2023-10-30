package com.example.demo.bus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bus.entity.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // @Query("SELECT D FROM Doctor d WHERE d.email = ?1")
    // Optional<Doctor> findDOctorByEmail(String Email);
    Optional<Destination> findByName(String name);

}
