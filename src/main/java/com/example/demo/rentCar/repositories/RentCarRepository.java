package com.example.demo.rentCar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.rentCar.entity.RentCar;

@Repository
public interface RentCarRepository extends JpaRepository<RentCar, Long> {
    Optional<RentCar> findByName(String fileName);

}
