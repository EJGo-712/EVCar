package com.evcar.service.charging;

import com.evcar.domain.charging.ChargingStation;
import com.evcar.repository.charging.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargingStationServiceImpl implements ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    @Override
    public List<ChargingStation> findByMapBounds(
            double swLat, double neLat,
            double swLng, double neLng
    ) {
        return chargingStationRepository.findByLatBetweenAndLngBetween(
                swLat, neLat, swLng, neLng
        );
    }

    // 🔥🔥🔥 이거 추가 (핵심)
    @Override
    public List<ChargingStation> findByRegion(String sido, String sigungu) {
        return chargingStationRepository.findBySidoAndSigungu(sido, sigungu);
    }
}