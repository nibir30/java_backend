package com.example.demo.bus.services;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.bus.dtos.AddBusDto;
import com.example.demo.bus.dtos.EditBusDto;
import com.example.demo.bus.entity.*;
import com.example.demo.bus.enums.RouteTypeEnum;
import com.example.demo.bus.repositories.*;
import com.example.demo.helpers.DebugHelper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final DestinationRepository destinationRepository;

    public List<BusEntity> getBus() {
        return busRepository.findAll();
    }

    public boolean addNewBus(AddBusDto busDto) {

        DebugHelper.printData(busDto.toString());

        BusEntity bus = new BusEntity();
        BeanUtils.copyProperties(busDto, bus);

        bus.setDestination(destinationRepository.findById(busDto.getDestinationId())
                .orElseThrow(() -> new IllegalStateException("Dest does not exit")));
        if (busDto.getRouteTypeString().equals("from")) {
            bus.setRouteType(RouteTypeEnum.FROM);
        } else {
            bus.setRouteType(RouteTypeEnum.TO);
        }
        BusEntity savedBus = busRepository.save(bus);
        DebugHelper.printData(savedBus.toString());

        return true;
    }

    public void deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Bus does not exit");
        }
    }

    @Transactional
    public boolean updateBus(Long id, EditBusDto busDto) {

        BusEntity newBus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Bus does not exit"));
        DebugHelper.printData(newBus.toString());

        BeanUtils.copyProperties(busDto, newBus);

        if (busDto.getDestinationId() != null) {
            Destination dest = destinationRepository.findById(busDto.getDestinationId())
                    .orElseThrow(() -> new IllegalStateException("Bus does not exit"));
            newBus.setDestination(dest);
        }
        if (busDto.getRouteTypeString() != null) {

            if (busDto.getRouteTypeString().equals("from")) {
                newBus.setRouteType(RouteTypeEnum.FROM);
            } else {
                newBus.setRouteType(RouteTypeEnum.TO);
            }
        }
        System.out.println(newBus.toString());
        return true;
    }

    public List<BusEntity> getBusbyType(String routeType, Long destid) {
        if (routeType.equals("from")) {
            return busRepository.findByRouteTypeAndDestinationId(RouteTypeEnum.FROM, destid);
        } else {
            return busRepository.findByRouteTypeAndDestinationId(RouteTypeEnum.TO, destid);
        }
    }
}
