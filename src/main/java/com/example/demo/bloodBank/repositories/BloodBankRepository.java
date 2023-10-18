package com.example.demo.bloodBank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bloodBank.entity.BloodBank;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    Optional<BloodBank> findByName(String fileName);

}
