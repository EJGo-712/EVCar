package com.evcar.service.vehicle;

import com.evcar.domain.vehicle.Vehicle;
import com.evcar.dto.vehicle.VehicleDetailDto;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.repository.vehicle.VehicleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehicleServiceImpl implements VehicleService {

    private static final String VEHICLE_STATUS_ACTIVE = "ACTIVE";

    private final VehicleRepository vehicleRepository;

    @Override
    public List<VehicleListDto> getVehicleList(String brand, String vehicleClass) {
        List<Vehicle> vehicles;

        boolean hasBrand = StringUtils.hasText(brand);
        boolean hasVehicleClass = StringUtils.hasText(vehicleClass);

        if (hasBrand && hasVehicleClass) {
            vehicles = vehicleRepository.findByVehicleStatusAndBrandAndVehicleClassOrderByCreatedAtDesc(
                    VEHICLE_STATUS_ACTIVE,
                    brand,
                    vehicleClass
            );
        } else if (hasBrand) {
            vehicles = vehicleRepository.findByVehicleStatusAndBrandOrderByCreatedAtDesc(
                    VEHICLE_STATUS_ACTIVE,
                    brand
            );
        } else if (hasVehicleClass) {
            vehicles = vehicleRepository.findByVehicleStatusAndVehicleClassOrderByCreatedAtDesc(
                    VEHICLE_STATUS_ACTIVE,
                    vehicleClass
            );
        } else {
            vehicles = vehicleRepository.findByVehicleStatusOrderByCreatedAtDesc(VEHICLE_STATUS_ACTIVE);
        }

        return vehicles.stream()
                .map(vehicle -> VehicleListDto.builder()
                        .vehicleId(vehicle.getVehicleId())
                        .brand(vehicle.getBrand())
                        .modelName(vehicle.getModelName())
                        .vehicleClass(vehicle.getVehicleClass())
                        .priceBasic(vehicle.getPriceBasic())
                        .imageUrl(vehicle.getImageUrl())
                        .build())
                .toList();
    }

    @Override
    public VehicleDetailDto getDetail(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량 정보를 찾을 수 없습니다."));

        return new VehicleDetailDto(
                vehicle.getVehicleId(),
                vehicle.getBrand(),
                vehicle.getModelName(),
                vehicle.getVehicleClass(),
                vehicle.getPriceBasic(),
                vehicle.getPricePremium(),
                vehicle.getDrivingRange(),
                vehicle.getFastChargingTime(),
                vehicle.getSlowChargingTime(),
                vehicle.getChargingMethod(),
                vehicle.getBatteryCapacity() == null ? null : vehicle.getBatteryCapacity().doubleValue(),
                vehicle.getImageUrl(),
                vehicle.getCatalogUrl(),
                vehicle.getVehicleUrl(),
                false
        );
    }
}