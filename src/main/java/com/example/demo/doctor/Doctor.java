package com.example.demo.doctor;

import jakarta.persistence.*;

@Entity
@Table
public class Doctor {
    @Override
    public String toString() {
        return "Doctor {id=" + id + ", name=" + name + ", dept=" + dept + ", symptoms=" + symptoms + ", degrees="
                + degrees + "}";
    }

    @Id
    @SequenceGenerator(name = "doctor_sequence", sequenceName = "doctor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_sequence")
    private Long id;
    private String name;
    private String dept;
    private String symptoms;
    // private List<String> symptoms;

    // private List<String> degrees;
    private String degrees;

    public Doctor() {
    }

    public Doctor(Long id, String name, String dept, String symptoms, String degrees) {

        // public Doctor(Long id, String name, String dept, List<String> symptoms,
        // List<String> degrees) {

        this.id = id;
        this.name = name;
        this.dept = dept;
        this.symptoms = symptoms;
        this.degrees = degrees;
    }

    public Doctor(String name, String dept, String symptoms, String degrees) {

        // public Doctor(Long id, String name, String dept, List<String> symptoms,
        // List<String> degrees) {

        this.name = name;
        this.dept = dept;
        this.symptoms = symptoms;
        this.degrees = degrees;
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

}
