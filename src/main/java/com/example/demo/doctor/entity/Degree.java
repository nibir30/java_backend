package com.example.demo.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Degree")
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String bangla_name;

}
