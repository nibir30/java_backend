package com.example.demo.bus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.bus.dtos.AddDestinationDto;
import com.example.demo.bus.entity.Destination;
import com.example.demo.bus.repositories.DestinationRepository;

import jakarta.transaction.Transactional;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getDestination() {
        return destinationRepository.findAll();
    }

    public boolean addNewDestination(AddDestinationDto destinationDto) {
        Optional<Destination> savedDestination = destinationRepository.findByName(destinationDto.getName());

        if (savedDestination.isPresent()) {
            return false;
        }
        Destination newDewDestination = new Destination();
        BeanUtils.copyProperties(destinationDto, newDewDestination);

        destinationRepository.save(newDewDestination);
        return true;

    }

    public void deleteDestination(Long id) {

        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Destination does not exit");
        }
    }

    @Transactional
    public void updateDestination(Long id, String name, String bangla_name) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Destination does not exit"));

        if (name != null && name.length() > 0) {
            destination.setName(name);
        }
        if (bangla_name != null && bangla_name.length() > 0) {
            destination.setBangla_name(bangla_name);
        }
    }

    @Transactional
    public boolean updateDestination(Long id, AddDestinationDto destinationDto) {
        Destination newDestination = destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Destination does not exit"));

        System.out.println(newDestination);

        if (destinationDto.getName() != null && destinationDto.getName().length() > 0) {
            newDestination.setName(destinationDto.getName());
        }
        if (destinationDto.getBangla_name() != null && destinationDto.getBangla_name().length() > 0) {
            newDestination.setBangla_name(destinationDto.getBangla_name());
        }

        return true;
    }
}
