package com.example.demo.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Hospital")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hospital {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String bangla_name;

    @Column(nullable = false)
    private String phone;

    private String address;

    @ManyToOne(targetEntity = HospitalDepartment.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_hospital_department", referencedColumnName = "id", nullable = false)
    private HospitalDepartment dept;

    private String image_file_path;
}