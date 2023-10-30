package com.example.demo.bus.entity;

import com.example.demo.bus.enums.RouteTypeEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Bus")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String bangla_name;

    @ManyToOne(targetEntity = Destination.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_bus_destination", referencedColumnName = "id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private RouteTypeEnum routeType;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String fee;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String phone;

    private String address;

    private String ticketLink;

}