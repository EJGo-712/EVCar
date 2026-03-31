package com.evcar.service.charging;

import com.evcar.domain.charging.ChargingStation;
import com.evcar.dto.charging.ChargingStationResponseDto;
import com.evcar.repository.charging.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargingStationServiceImpl implements ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    // 🔵 지도 범위 조회
    @Override
    public List<ChargingStation> findByMapBounds(
            double swLat, double neLat,
            double swLng, double neLng
    ) {
        return chargingStationRepository.findByLatBetweenAndLngBetween(
                swLat, neLat, swLng, neLng
        );
    }

    // 🔵 지역 조회 (엔티티 그대로)
    @Override
    public List<ChargingStation> findByRegion(String sido, String sigungu) {
        return chargingStationRepository.findBySidoAndSigungu(sido, sigungu);
    }

    // 🔥🔥🔥 핵심: 지역 → zcode → 전체 조회
    @Override
    public List<ChargingStationResponseDto> getStationsByRegion(String sido, String sigungu) {

        // 1️⃣ 지역으로 조회해서 zcode 찾기
        List<ChargingStation> regionList = chargingStationRepository.findBySidoAndSigungu(sido, sigungu);

        if (regionList.isEmpty()) {
            return List.of();
        }

        // 2️⃣ zcode 추출
        String zcode = regionList.get(0).getZcode();

        // 3️⃣ zcode로 전체 충전소 조회
        List<ChargingStation> list = chargingStationRepository.findByZcode(zcode);

        // 4️⃣ DTO 변환
        return list.stream()
                .map(s -> ChargingStationResponseDto.builder()
                        .stationId(s.getStationId())
                        .stationName(s.getStationName())
                        .lat(s.getLat())
                        .lng(s.getLng())
                        .build())
                .toList();
    }

    // 🔵 기존 유지 (필요하면 사용)
    @Override
    public List<ChargingStationResponseDto> getStationsByZcode(String zcode) {

        List<ChargingStation> list = chargingStationRepository.findByZcode(zcode);

        return list.stream()
                .map(s -> ChargingStationResponseDto.builder()
                        .stationId(s.getStationId())
                        .stationName(s.getStationName())
                        .lat(s.getLat())
                        .lng(s.getLng())
                        .build())
                .toList();
    }
}