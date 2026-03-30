package com.evcar.controller.charging;

import com.evcar.domain.charging.ChargingStation;
import com.evcar.service.charging.ChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/charging")
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    // ✅ 1. 지도 페이지 이동
    @GetMapping("/map-page")
    public String mapPage() {
        return "charging/map";
    }

    // ✅ 2. 지도 범위 기반 조회 (지도 이동 시 자동 호출)
    @GetMapping("/map")
    @ResponseBody
    public List<ChargingStation> getMap(
            @RequestParam("swLat") double swLat,
            @RequestParam("swLng") double swLng,
            @RequestParam("neLat") double neLat,
            @RequestParam("neLng") double neLng
    ) {
        return chargingStationService.findByMapBounds(swLat, neLat, swLng, neLng);
    }

    // ✅ 3. 🔥 지역 선택 조회 (이게 핵심 추가)
    @GetMapping("/region")
    @ResponseBody
    public List<ChargingStation> getByRegion(
            @RequestParam("sido") String sido,
            @RequestParam("sigungu") String sigungu
    ) {
        return chargingStationService.findByRegion(sido, sigungu);
    }
}