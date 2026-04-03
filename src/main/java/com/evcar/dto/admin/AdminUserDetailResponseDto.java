package com.evcar.dto.admin;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminUserDetailResponseDto {

    private String userId;
    private String loginId;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
    private String email;
    private String userStatus;
    private String role;
    private String vehicleModel;
    private String vehicleYear;
    private Integer drivingDistance;
    private String createdAt;
    private String updatedAt;

    public String getHasVehicle() {
        boolean hasModel = vehicleModel != null && !vehicleModel.isBlank();
        boolean hasYear = vehicleYear != null && !vehicleYear.isBlank();
        boolean hasDistance = drivingDistance != null && drivingDistance > 0;
        return (hasModel || hasYear || hasDistance) ? "yes" : "no";
    }
}