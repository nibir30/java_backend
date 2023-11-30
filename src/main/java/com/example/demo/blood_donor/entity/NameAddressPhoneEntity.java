package com.example.demo.blood_donor.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "NameAddressPhone")
@Table
@Data
@Builder
public class NameAddressPhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;

    private String bangleName;

    private String address;

    private String phone;
}
