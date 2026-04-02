// src/main/java/com/evcar/repository/charging/ChargingStationRepository.java
package com.evcar.repository.charging;

import com.evcar.domain.charging.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {

    List<ChargingStation> findByAddressContaining(String keyword);
}