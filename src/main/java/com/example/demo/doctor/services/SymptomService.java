package com.example.demo.doctor.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.entity.SymptomImage;
import com.example.demo.doctor.entity.Symptom;
import com.example.demo.doctor.repositories.ImageSympRepository;
import com.example.demo.doctor.repositories.SymptomRepository;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SymptomService {
    private final SymptomRepository symptomRepository;
    private final ImageSympRepository imageRepository;

    public List<Symptom> getSymptoms() {
        return symptomRepository.findAll();
    }

    public Map<String, Object> addNewSymptom(Symptom symptom) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (symptomRepository.existsById(symptom.getId())) {
            result.put("id", symptom.getId());
            result.put("message", "Symptom already exists");
            result.put("isSuccess", false);

            return result;
        }
        symptomRepository.save(symptom);
        result.put("id", symptom.getId());
        result.put("message", "Symptom added successfully");
        result.put("isSuccess", true);

        System.out.println(symptom);

        return result;
    }

    private final String FOLDER_PATH = "D:/Dev/Backend/Java/tngl/java_backend/src/images/symptoms/";

    public SymptomImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        SymptomImage data = SymptomImage.builder()
                .name("SYMPTOM" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("symptoms")
                .build();
        SymptomImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String filePath = FOLDER_PATH + "SYMPTOM" + savedFile.getId().toString() + file.getOriginalFilename();

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName("SYMPTOM" + savedFile.getId().toString() + file.getOriginalFilename());

        SymptomImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        if (final_data != null || file != null) {
            return final_data;
        }
        return null;

    }

    public void deleteSymptom(Long id) {

        if (symptomRepository.existsById(id)) {
            symptomRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Symptom does not exit");
        }
    }

    @Transactional
    public void updateSymptom(Long id, String name, String bangla_name) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        if (name != null && name.length() > 0) {
            symptom.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            symptom.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public void updateSymptom(Long id, Symptom symptom) {
        Symptom newSymptom = symptomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Symptom does not exit"));

        System.out.println(newSymptom);

        if (symptom.getName() != null && symptom.getName().length() > 0) {
            newSymptom.setName(symptom.getName());
        }
        if (symptom.getBangla_name() != null && symptom.getBangla_name().length() > 0) {
            newSymptom.setBangla_name(symptom.getBangla_name());
        }
        if (symptom.getFile_path() != null && symptom.getFile_path().length() > 0) {
            newSymptom.setFile_path(symptom.getFile_path());
        }
    }
}
