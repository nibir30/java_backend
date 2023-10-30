package com.example.demo.bus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bus.entity.BusEntity;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {
}
