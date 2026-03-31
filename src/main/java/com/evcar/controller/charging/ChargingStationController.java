package com.evcar.controller.charging;

import com.evcar.dto.charging.ChargingStationResponseDto;
import com.evcar.service.charging.ChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/charging")
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    // 지도 페이지
    @GetMapping("/map-page")
    public String mapPage() {
        return "charging/map";
    }

    // 지역 검색 API
    @GetMapping("/region")
    @ResponseBody
    public List<ChargingStationResponseDto> getRegion(
            @RequestParam(name = "sido") String sido,
            @RequestParam(name = "sigungu") String sigungu
    ) {
        return chargingStationService.getStationsByRegion(sido, sigungu);
    }
}