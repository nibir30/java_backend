package com.example.demo.blood_donor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Blood_organization")
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationModel {
    @Id
    @Column(nullable = false)
    private Long id;

    private String name;

    private String bangla_name;

    private String image_file_path;
}
