package com.example.demo.blood_donor.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.blood_donor.dtos.AddOrganizationDto;
import com.example.demo.blood_donor.entity.OrganizationImage;
import com.example.demo.blood_donor.entity.OrganizationModel;
import com.example.demo.blood_donor.repositories.ImageOranizationRepository;
import com.example.demo.blood_donor.repositories.OrganizationRepository;
import com.example.demo.helpers.AppConstant;
import com.example.demo.helpers.DebugHelper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ImageOranizationRepository imageRepository;

    public List<OrganizationModel> getOrganizations() {
        return organizationRepository.findAll();
    }

    public boolean addNewOrganization(AddOrganizationDto organizationDto) {
        Optional<OrganizationModel> existingType = organizationRepository.findByName(organizationDto.getName());

        if (existingType.isPresent()) {
            return false;
        }
        OrganizationModel newType = new OrganizationModel();
        BeanUtils.copyProperties(organizationDto, newType);
        // newType.se
        organizationRepository.save(newType);
        return true;

    }

    public void deleteOrganization(Long id) {

        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Organization does not exit");
        }
    }

    // @Transactional
    // public OrganizationModel updateOrganization(Long id, EditOrganizationDto
    // organization) {
    // OrganizationModel newOrganization = organizationRepository.findById(id)
    // .orElseThrow(() -> new IllegalStateException("Organization does not exit"));

    // System.out.println(newOrganization);

    // if (organization.getName() != null && organization.getName().length() > 0) {
    // Optional<OrganizationModel> existingOrganization = organizationRepository
    // .findByName(organization.getName());

    // if (existingOrganization.isPresent()) {
    // return null;
    // }
    // newOrganization.setName(organization.getName());
    // }
    // if (organization.getAddress() != null && organization.getAddress().length() >
    // 0) {
    // newOrganization.setAddress(organization.getAddress());
    // }
    // if (organization.getContact() != null && organization.getContact().length() >
    // 0) {
    // newOrganization.setContact(organization.getContact());
    // }
    // return newOrganization;
    // }

    private final String FOLDER_PATH = AppConstant.folder_path + "/organizations/";

    public byte[] getImage(Long id) throws IOException {

        Optional<OrganizationImage> savedFile = imageRepository.findById(id);

        if (savedFile.isPresent()) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);
            byte[] image = Files.readAllBytes(new File(filePath).toPath());
            return image;
        } else {
            return null;
        }
    }

    public OrganizationImage uploadImageToFileSystem(MultipartFile file) throws IOException {

        OrganizationImage data = OrganizationImage.builder()
                .name("Organization" + file.getOriginalFilename())
                .type(file.getContentType())
                .category("organizations")
                .build();
        OrganizationImage savedFile = imageRepository.save(data);

        DebugHelper.printData(savedFile.toString());
        String name = "";
        if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")) {
            name = "Organization" + savedFile.getId().toString() + "_" + file.getOriginalFilename();
        } else {
            name = "Organization" + savedFile.getId().toString() + "_" + file.getOriginalFilename() + ".png";
        }
        String filePath = FOLDER_PATH + name;

        DebugHelper.printData(filePath);

        file.transferTo(new File(filePath));
        savedFile.setFilePath(filePath);
        savedFile.setName(name);

        OrganizationImage final_data = imageRepository.save(savedFile);
        DebugHelper.printData(final_data.toString());

        return final_data;

    }

    public OrganizationImage updateImageFromFileSystem(MultipartFile file, String id) throws IOException {

        Optional<OrganizationImage> savedFile = imageRepository.findById(Long.parseLong(id));

        if (savedFile != null) {
            DebugHelper.printData(savedFile.toString());
            String filePath = savedFile.get().getFilePath();

            DebugHelper.printData(filePath);

            file.transferTo(new File(filePath));
            OrganizationImage final_data = imageRepository.save(savedFile.get());
            DebugHelper.printData(final_data.toString());

            return final_data;
        } else {
            return null;
        }
    }
}
