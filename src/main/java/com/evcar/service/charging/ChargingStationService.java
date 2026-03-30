package com.evcar.service.charging;

import com.evcar.domain.charging.ChargingStation;

import java.util.List;

public interface ChargingStationService {

    List<ChargingStation> findByMapBounds(
            double swLat, double neLat,
            double swLng, double neLng
    );

    List<ChargingStation> findByRegion(String sido, String sigungu);
}