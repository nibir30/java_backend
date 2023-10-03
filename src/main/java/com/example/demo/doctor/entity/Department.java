package com.example.demo.doctor.entity;

import jakarta.persistence.*;

@Entity(name = "Department")
@Table
public class Department {
    @Override
    public String toString() {
        return "Department {id=" + id + ", name=" + name + ", bangla_name=" + bangla_name + "}";
    }

    @Id
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
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

    public Department() {
    }

    public Department(String name, String bangla_name) {
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
