package com.example.demo.lawyer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "lawyer")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String bangla_name;
    @Column(nullable = false)
    private String phone;

    @ManyToOne(targetEntity = LawyerPracticeType.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_lawyer_practice", referencedColumnName = "id", nullable = false)
    private LawyerPracticeType practiceType;

    @ManyToOne(targetEntity = LawyerCourt.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_lawyer_court", referencedColumnName = "id", nullable = false)
    private LawyerCourt court;
}