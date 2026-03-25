package com.evcar.service.vehicle;

import com.evcar.domain.vehicle.Vehicle;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public List<VehicleListDto> getVehicleList(String brand, String vehicleClass) {

        List<Vehicle> vehicles;

        // 🔥 필터 조건
        if ("전체".equals(brand) && "전체".equals(vehicleClass)) {
            vehicles = vehicleRepository.findAll();

        } else if (!"전체".equals(brand) && "전체".equals(vehicleClass)) {
            vehicles = vehicleRepository.findByBrand(brand);

        } else if ("전체".equals(brand) && !"전체".equals(vehicleClass)) {
            vehicles = vehicleRepository.findByVehicleClass(vehicleClass);

        } else {
            vehicles = vehicleRepository.findByBrandAndVehicleClass(brand, vehicleClass);
        }

        // 🔥 DTO 변환 (핵심)
        return vehicles.stream()
                .map(v -> {
                    VehicleListDto dto = new VehicleListDto();

                    dto.setVehicleId(v.getVehicleId());
                    dto.setBrand(v.getBrand());
                    dto.setModelName(v.getModelName());
                    dto.setVehicleClass(v.getVehicleClass());
                    dto.setPriceBasic(v.getPriceBasic());
                    dto.setPricePremium(v.getPricePremium());
                    dto.setDrivingRange(v.getDrivingRange());
                    dto.setImageUrl(v.getImageUrl());
                    dto.setCatalogUrl(v.getCatalogUrl());

                    return dto;
                })
                .toList();
    }
}