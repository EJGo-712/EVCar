package com.evcar.repository.vehicle;

import com.evcar.domain.vehicle.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByVehicleStatusOrderByCreatedAtDesc(String vehicleStatus);

    List<Vehicle> findByVehicleStatusAndBrandOrderByCreatedAtDesc(String vehicleStatus, String brand);

    List<Vehicle> findByVehicleStatusAndVehicleClassOrderByCreatedAtDesc(String vehicleStatus, String vehicleClass);

    List<Vehicle> findByVehicleStatusAndBrandAndVehicleClassOrderByCreatedAtDesc(
            String vehicleStatus,
            String brand,
            String vehicleClass
    );
}