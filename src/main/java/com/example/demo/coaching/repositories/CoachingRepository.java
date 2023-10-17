package com.example.demo.coaching.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.coaching.entity.Coaching;

@Repository
public interface CoachingRepository extends JpaRepository<Coaching, Long> {
    Optional<Coaching> findByName(String fileName);

}
