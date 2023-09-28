package com.example.demo.doctor.symptom;

import jakarta.persistence.*;

@Entity(name = "Symptom")
@Table
public class Symptom {
    @Override
    public String toString() {
        return "Symptom {id=" + id + ", name=" + name +", bangla_name=" + bangla_name + "}";
    }

    @Id
    @SequenceGenerator(name = "symptom_sequence", sequenceName = "symptom_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "symptom_sequence")
    @Column(updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    private String name;    
    private String bangla_name;

    public String getBangla_name() {
        return bangla_name;
    }

    public void setBangla_name(String bangla_name) {
        this.bangla_name = bangla_name;
    }

    public Symptom() {
    }

    public Symptom(String name, String bangla_name) {
        this.name = name;
        this.bangla_name = bangla_name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
