package com.evcar.domain.vehicle;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String brand;              // 현대 / 기아

    private String modelName;          // 차량명

    private String vehicleClass;       // 차급 (소형SUV 등)

    private String vehicleStatus;      // ACTIVE

    private int priceBasic;

    private int pricePremium;

    private int drivingRange;

    private String fastChargingTime;

    private String slowChargingTime;

    private String chargingMethod;

    private double batteryCapacity;

    private String imageUrl;

    private String catalogUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}