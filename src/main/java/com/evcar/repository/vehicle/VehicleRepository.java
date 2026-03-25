package com.evcar.repository.vehicle;

import com.evcar.domain.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByBrand(String brand);

    List<Vehicle> findByVehicleClass(String vehicleClass);

    List<Vehicle> findByBrandAndVehicleClass(String brand, String vehicleClass);
}