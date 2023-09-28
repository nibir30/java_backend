package com.example.demo.doctor.doctors;

import jakarta.persistence.*;

@Entity(name = "Doctor")
@Table
public class Doctor {
    @Override
    public String toString() {
        return "Doctor {id=" + id + ", name=" + name + ", dept=" + dept + ", symptoms=" + symptoms + ", degrees="
                + degrees + ", phone=" + phone + "}";
    }

    @Id
    @SequenceGenerator(name = "doctor_sequence", sequenceName = "doctor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_sequence")
    @Column(updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    private String name;
    private String dept;
    private String symptoms;
    @Column(nullable = false, name = "phone", unique = true)
    private String phone;
    private String degrees;

    // private List<String> symptoms;
    // private List<String> degrees;

    public Doctor() {
    }

    public Doctor(Long id, String name, String dept, String symptoms, String degrees, String phone) {

        // public Doctor(Long id, String name, String dept, List<String> symptoms,
        // List<String> degrees) {

        this.id = id;
        this.name = name;
        this.dept = dept;
        this.symptoms = symptoms;
        this.degrees = degrees;
        this.phone = phone;
    }

    public Doctor(String name, String dept, String symptoms, String degrees, String phone) {

        // public Doctor(Long id, String name, String dept, List<String> symptoms,
        // List<String> degrees) {

        this.name = name;
        this.dept = dept;
        this.symptoms = symptoms;
        this.degrees = degrees;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}