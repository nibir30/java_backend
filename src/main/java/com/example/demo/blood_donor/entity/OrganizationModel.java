package com.example.demo.blood_donor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity(name = "Blood_organization")
@Table
@Data
@Builder
public class OrganizationModel {
    @Id
    @Column(nullable = false)
    private Long id;

    private String name;

    private String bangla_name;

    private String image_file_path;
}
