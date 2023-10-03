package com.example.demo.doctor.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Doctor")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Override
    public String toString() {
        return "Doctor [id=" + id + ", name=" + name + ", phone=" + phone + ", dept_id=" + dept_id + ", degree="
                + degree + ", symptom=" + symptom + "]";
    }

    @Id
    @SequenceGenerator(name = "doctor_sequence", sequenceName = "doctor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_sequence")
    @Column(updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, name = "phone", unique = true)
    private String phone;
    private Long dept_id;
    private List<Long> degree;
    private List<Long> symptom;
    private String image_file_path;

    public Doctor(String name, String phone, Long dept_id,
            List<Long> degree, List<Long> symptom, String imageFilePath) {

        this.name = name;
        this.phone = phone;
        this.dept_id = dept_id;
        this.degree = degree;
        this.symptom = symptom;
        this.image_file_path = imageFilePath;
    }

}