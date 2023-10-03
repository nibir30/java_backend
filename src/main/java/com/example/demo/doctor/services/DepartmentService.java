package com.example.demo.doctor.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.doctor.entity.Department;
import com.example.demo.doctor.repositories.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
    private final DepartmentRepository deptRepository;

    public DepartmentService(DepartmentRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    public List<Department> getDepts() {
        return deptRepository.findAll();
    }

    public void addNewDept(Department dept) {
        deptRepository.save(dept);
        System.out.println(dept);
    }

    public void deleteDept(Long id) {

        if (deptRepository.existsById(id)) {
            deptRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Dept does not exit");
        }
    }

    @Transactional
    public void updateDept(Long id, String name, String bangla_name) {
        Department dept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        if (name != null && name.length() > 0) {
            dept.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            dept.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateDept(Long id, Department dept) {
        Department newDept = deptRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Dept does not exit"));

        System.out.println(newDept);

        if (dept.getName() != null && dept.getName().length() > 0) {
            newDept.setName(dept.getName());
        }
        if (dept.getBangla_name() != null && dept.getBangla_name().length() > 0) {
            newDept.setBangla_name(dept.getBangla_name());
        }
    }
}
