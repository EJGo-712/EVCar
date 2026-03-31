package com.evcar.repository.charging;

import com.evcar.domain.charging.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {

    // 지도 범위 조회
    List<ChargingStation> findByLatBetweenAndLngBetween(
            double swLat, double neLat,
            double swLng, double neLng
    );

    // 지역 조회
    List<ChargingStation> findBySidoAndSigungu(String sido, String sigungu);

    // 🔥 추가 (이거 한 줄만)
    List<ChargingStation> findByZcode(String zcode);
}