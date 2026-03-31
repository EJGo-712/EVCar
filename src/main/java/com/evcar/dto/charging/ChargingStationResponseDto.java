package com.evcar.dto.charging;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargingStationResponseDto {

    private String stationId;
    private String stationName;
    private Double lat;
    private Double lng;
}