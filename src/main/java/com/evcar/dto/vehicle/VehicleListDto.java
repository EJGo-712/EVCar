package com.evcar.dto.vehicle;

public class VehicleListDto {

    private Long vehicleId;
    private String brand;
    private String modelName;
    private String vehicleClass;
    private Integer priceBasic;
    private Integer pricePremium;
    private Integer drivingRange;
    private String imageUrl;
    private String catalogUrl;
    private boolean wished;   // 추가됨 (이미 있음)

    // 생성자
    public VehicleListDto() {}

    public VehicleListDto(Long vehicleId, String brand, String modelName,
                          String vehicleClass, Integer priceBasic,
                          Integer pricePremium, Integer drivingRange,
                          String imageUrl, String catalogUrl) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleClass = vehicleClass;
        this.priceBasic = priceBasic;
        this.pricePremium = pricePremium;
        this.drivingRange = drivingRange;
        this.imageUrl = imageUrl;
        this.catalogUrl = catalogUrl;
    }

    // Getters & Setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getVehicleClass() { return vehicleClass; }
    public void setVehicleClass(String vehicleClass) { this.vehicleClass = vehicleClass; }

    public Integer getPriceBasic() { return priceBasic; }
    public void setPriceBasic(Integer priceBasic) { this.priceBasic = priceBasic; }

    public Integer getPricePremium() { return pricePremium; }
    public void setPricePremium(Integer pricePremium) { this.pricePremium = pricePremium; }

    public Integer getDrivingRange() { return drivingRange; }
    public void setDrivingRange(Integer drivingRange) { this.drivingRange = drivingRange; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCatalogUrl() { return catalogUrl; }
    public void setCatalogUrl(String catalogUrl) { this.catalogUrl = catalogUrl; }

    // 여기 추가 (핵심)
    public boolean isWished() {
        return wished;
    }

    public void setWished(boolean wished) {
        this.wished = wished;
    }

    // 가격 포맷 (만원 단위)
    public String getPriceBasicFormatted() {
        if (priceBasic == null) return "-";
        if (pricePremium != null) {
            return String.format("%,d만원 ~ %,d만원", priceBasic, pricePremium);
        }
        return String.format("%,d만원~", priceBasic);
    }
}