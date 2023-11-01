package com.example.demo.doctor.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Doctor")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean homeService;

    @ManyToOne(targetEntity = Department.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_doctor_department", referencedColumnName = "id", nullable = false)
    private Department dept;

    @ManyToMany(targetEntity = Degree.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_doctor_degree", referencedColumnName = "id")
    private List<Degree> degree;

    @ManyToMany(targetEntity = Symptom.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_doctor_symptom", referencedColumnName = "id")
    private List<Symptom> symptom;
    private String image_file_path;
}