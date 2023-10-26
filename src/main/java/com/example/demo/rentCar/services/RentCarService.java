package com.example.demo.rentCar.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.rentCar.entity.RentCarImage;
import com.example.demo.helpers.AppConstant;
import com.example.demo.helpers.DebugHelper;
import com.example.demo.rentCar.dto.AddRentCarDto;
import com.example.demo.rentCar.dto.EditRentCarDto;
import com.example.demo.rentCar.entity.RentCar;
import com.example.demo.rentCar.repositories.ImageRentCarRepository;
import com.example.demo.rentCar.repositories.RentCarRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RentCarService {
    private final RentCarRepository rentCarRepository;
    private final ImageRentCarRepository imageRepository;

    public List<RentCar> getRentCars() {
        return rentCarRepository.findAll();
    }

    public boolean addNewRentCar(AddRentCarDto rentCarDto) {
        Optional<RentCar> existingType = rentCarRepository.findByName(rentCarDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        RentCar newType = new RentCar();
        BeanUtils.copyProperties(rentCarDto, newType);

        rentCarRepository.save(newType);
        return true;

    }

    public void deleteRentCar(Long id) {

        if (rentCarRepository.existsById(id)) {
            rentCarRepository.deleteById(id);
        } else {
            throw new IllegalStateException("RentCar does not exit");
        }
    }

    @Transactional
    public RentCar updateRentCar(Long id, EditRentCarDto rentCar) {
        RentCar newRentCar = rentCarRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("RentCar does not exit"));

        System.out.println(newRentCar);

        if (rentCar.getName() != null && rentCar.getName().length() > 0) {
            Optional<RentCar> existingRentCar = rentCarRepository.findByName(rentCar.getName());

            if (existingRentCar.isPresent()) {
                return null;
            }
            newRentCar.setName(rentCar.getName());
        }
        if (rentCar.getAddress() != null && rentCar.getAddress().length() > 0) {
            newRentCar.setAddress(rentCar.getAddress());
        }
        if (rentCar.getContact() != null && rentCar.getContact().length() > 0) {
            newRentCar.setContact(rentCar.getContact());
        }
        return newRentCar;
    }

    private final String FOLDER_PATH = AppConstant.folder_path + "/rentCars/";

    public byte[] getImage(Long id) throws IOException {

        Optional<RentCarImage> savedFile = imageRepository.findById(id);

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

    public RentCarImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        RentCarImage data = RentCarImage.builder()
                .name("RentCar" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("rentCars")
                .build();
        RentCarImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String name = "";
        if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")) {
            name = "RentCar" + savedFile.getId().toString() + "_" + file.getOriginalFilename();
        } else {
            name = "RentCar" + savedFile.getId().toString() + "_" + file.getOriginalFilename() + ".png";
        }
        String filePath = FOLDER_PATH + name;

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName(name);

        RentCarImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public RentCarImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<RentCarImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            RentCarImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
    }
}
