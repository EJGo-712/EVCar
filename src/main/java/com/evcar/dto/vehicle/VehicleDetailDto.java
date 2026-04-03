package com.evcar.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDetailDto {

    private String vehicleId;
    private String brand;
    private String modelName;
    private String vehicleClass;

    private Integer priceBasic;
    private Integer pricePremium;

    private Integer drivingRange;
    private String fastChargingTime;
    private String slowChargingTime;
    private String chargingMethod;

    private Double batteryCapacity;

    private String imageUrl;
    private String catalogUrl;
    private String vehicleUrl;

    private boolean wished;
}