package com.example.demo.blood_donor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Blood_donor")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodDonor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String bangla_name;

    private String chairman_name;
    private String chairman_phone;
    private String chairman_address;

    private String generalSecretary_name;
    private String generalSecretary_phone;
    private String generalSecretary_address;

    @ManyToOne(targetEntity = OrganizationModel.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_organization", referencedColumnName = "id")
    private OrganizationModel organization;
}