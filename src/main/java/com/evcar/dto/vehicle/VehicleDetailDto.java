package com.evcar.dto.vehicle;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor   //  기본 생성자 (필수)
@AllArgsConstructor  //  전체 생성자 (JPQL 대비)
public class VehicleDetailDto {

    // 기본 정보
    private Long vehicleId;
    private String brand;
    private String modelName;
    private String vehicleClass;

    // 가격
    private int priceBasic;
    private int pricePremium;

    // 주행 / 충전
    private int drivingRange;
    private String fastChargingTime;
    private String slowChargingTime;
    private String chargingMethod;

    // 배터리
    private double batteryCapacity;

    // 이미지 & 링크
    private String imageUrl;
    private String catalogUrl;

    // 🔥 위시리스트 여부 (기본값 false)
    private boolean wished = false;
    
}