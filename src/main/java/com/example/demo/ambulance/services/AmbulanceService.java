package com.example.demo.ambulance.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.ambulance.dto.AddAmbulanceDto;
import com.example.demo.ambulance.dto.EditAmbulanceDto;
import com.example.demo.ambulance.entity.Ambulance;
import com.example.demo.ambulance.repositories.AmbulanceRepository;
import com.example.demo.ambulance.repositories.ImageAmbulanceRepository;
import com.example.demo.ambulance.entity.AmbulanceImage;
import com.example.demo.helpers.AppConstant;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;
    private final ImageAmbulanceRepository imageRepository;

    public List<Ambulance> getAmbulances() {
        return ambulanceRepository.findAll();
    }

    public boolean addNewAmbulance(AddAmbulanceDto ambulanceDto) {
        Optional<Ambulance> existingType = ambulanceRepository.findByName(ambulanceDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        Ambulance newType = new Ambulance();
        BeanUtils.copyProperties(ambulanceDto, newType);

        ambulanceRepository.save(newType);
        return true;

    }

    public void deleteAmbulance(Long id) {

        if (ambulanceRepository.existsById(id)) {
            ambulanceRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Ambulance does not exit");
        }
    }

    @Transactional
    public Ambulance updateAmbulance(Long id, EditAmbulanceDto ambulance) {
        Ambulance newAmbulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ambulance does not exit"));

        System.out.println(newAmbulance);

        if (ambulance.getName() != null && ambulance.getName().length() > 0) {
            Optional<Ambulance> existingAmbulance = ambulanceRepository.findByName(ambulance.getName());

            if (existingAmbulance.isPresent()) {
                return null;
            }
            newAmbulance.setName(ambulance.getName());
        }
        if (ambulance.getAddress() != null && ambulance.getAddress().length() > 0) {
            newAmbulance.setAddress(ambulance.getAddress());
        }
        if (ambulance.getContact() != null && ambulance.getContact().length() > 0) {
            newAmbulance.setContact(ambulance.getContact());
        }
        return newAmbulance;
    }

    private final String FOLDER_PATH = AppConstant.folder_path + "/ambulances/";

    public byte[] getImage(Long id) throws IOException {

        Optional<AmbulanceImage> savedFile = imageRepository.findById(id);

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

    public AmbulanceImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        AmbulanceImage data = AmbulanceImage.builder()
                .name("Ambulance" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("ambulances")
                .build();
        AmbulanceImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String name = "";
        if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")) {
            name = "Ambulance" + savedFile.getId().toString() + "_" + file.getOriginalFilename();
        } else {
            name = "Ambulance" + savedFile.getId().toString() + "_" + file.getOriginalFilename() + ".png";
        }
        String filePath = FOLDER_PATH + name;

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName(name);

        AmbulanceImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

}
