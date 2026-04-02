package com.evcar.domain.charging;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "charging_station")
public class ChargingStation {

    @Id
    @Column(name = "station_id", length = 50, nullable = false)
    private String stationId;

    @Column(name = "station_name", length = 100)
    private String stationName;

    @Column(name = "address", length = 255)
    private String address;

    // ✅ 수정된 부분
    @Column(name = "lat")
    private Double lat;

    // ✅ 수정된 부분
    @Column(name = "lng")
    private Double lng;

    @Column(name = "use_time", length = 100)
    private String useTime;

    @Column(name = "zcode", length = 20)
    private String zcode;

    @Column(name = "operator_name", length = 100)
    private String operatorName;

    @Column(name = "operator_call", length = 20)
    private String operatorCall;

    @Column(name = "parking_free", length = 20)
    private String parkingFree;

    @Column(name = "note")
    private String note;
}