package com.example.demo.blood_donor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Donor")
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String gender;

    @ManyToOne(targetEntity = BloodGroup.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_bloodGroup", referencedColumnName = "id", nullable = false)
    private BloodGroup bloodGroup;

}