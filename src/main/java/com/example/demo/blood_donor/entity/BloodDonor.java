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

    @OneToOne(targetEntity = NameAddressPhoneEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_chairman", referencedColumnName = "id")
    private NameAddressPhoneEntity chairman;
    @OneToOne(targetEntity = NameAddressPhoneEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_generalSecretary", referencedColumnName = "id")
    private NameAddressPhoneEntity generalSecretary;
    @OneToOne(targetEntity = NameAddressPhoneEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_orgSecretary", referencedColumnName = "id")
    private NameAddressPhoneEntity orgSecretary;

    @ManyToOne(targetEntity = OrganizationModel.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_donor_organization", referencedColumnName = "id")
    private OrganizationModel organization;
}