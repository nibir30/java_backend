package com.example.demo.doctor.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.doctor.entity.SymptomImage;
import com.example.demo.doctor.entity.Symptom;
import com.example.demo.doctor.repositories.ImageSympRepository;
import com.example.demo.doctor.repositories.SymptomRepository;
import com.example.demo.helpers.AppConstant;
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
            result.put("success", false);

            return result;
        }
        symptomRepository.save(symptom);
        result.put("id", symptom.getId());
        result.put("message", "Symptom added successfully");
        result.put("success", true);

        System.out.println(symptom);

        return result;
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

    private final String FOLDER_PATH = AppConstant.folder_path + "/symptoms/";

    public SymptomImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        SymptomImage data = SymptomImage.builder()
                .name("SYMPTOM" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("symptoms")
                .build();
        SymptomImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String name = "";
        if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")) {
            name = "SYMPTOM" + savedFile.getId().toString() + "_" + file.getOriginalFilename();
        } else {
            name = "SYMPTOM" + savedFile.getId().toString() + "_" + file.getOriginalFilename() + ".png";
        }
        String filePath = FOLDER_PATH + name;

        DebugHelper.printData(filePath);

        if (file != null) {
            file.transferTo(new File(filePath));
        }
        savedFile.setFilePath(filePath);
        savedFile.setName(name);

        SymptomImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public SymptomImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<SymptomImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            SymptomImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }

    }

    public byte[] getImage(Long id) throws IOException {

        Optional<SymptomImage> savedFile = imageRepository.findById(id);

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);
            byte[] image = Files.readAllBytes(new File(filePath).toPath());
            return image;
        } else {
            return null;
        }
    }
}
