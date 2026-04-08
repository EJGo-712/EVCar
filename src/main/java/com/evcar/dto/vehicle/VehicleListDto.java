package com.evcar.dto.vehicle;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleListDto {

    private String vehicleId;
    private String brand;
    private String modelName;
    private String vehicleClass;
    private Integer priceBasic;
    private Integer pricePremium;
    private Integer drivingRange;
    private String imageUrl;
    private String catalogUrl;
    private boolean wished;

    // 가격 포맷 (만원 단위)
    public String getPriceBasicFormatted() {
        if (priceBasic == null) return "-";
        if (pricePremium != null && pricePremium > 0) {
            return String.format("%,d만원 ~ %,d만원", priceBasic, pricePremium);
        }
        return String.format("%,d만원~", priceBasic);
    }
}