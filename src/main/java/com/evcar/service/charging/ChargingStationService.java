package com.evcar.service.charging;

import com.evcar.domain.charging.ChargingStation;
import com.evcar.dto.charging.ChargingStationResponseDto;

import java.util.List;

public interface ChargingStationService {

    List<ChargingStation> findByMapBounds(
            double swLat, double neLat,
            double swLng, double neLng
    );

    List<ChargingStation> findByRegion(String sido, String sigungu);

    // 🔥 이걸로 맞춰
    List<ChargingStationResponseDto> getStationsByRegion(String sido, String sigungu);

    // (선택) 유지하려면 같이 선언
    List<ChargingStationResponseDto> getStationsByZcode(String zcode);
}