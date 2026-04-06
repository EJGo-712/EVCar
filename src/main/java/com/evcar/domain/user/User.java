package com.evcar.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "login_id", nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "address_detail", nullable = false, length = 255)
    private String addressDetail;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "user_status", nullable = false, length = 10)
    @Builder.Default
    private String userStatus = "ACTIVE";

    @Column(name = "vehicle_model", nullable = false, length = 50)
    private String vehicleModel;

    @Column(name = "vehicle_year", nullable = false, length = 10)
    private String vehicleYear;

    @Column(name = "driving_distance", nullable = false)
    private Integer drivingDistance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "withdrawn_at")
    private LocalDateTime withdrawnAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.role == null || this.role.isBlank()) {
            this.role = "USER";
        }

        if (this.userStatus == null || this.userStatus.isBlank()) {
            this.userStatus = "ACTIVE";
        }

        if (this.vehicleModel == null) {
            this.vehicleModel = "";
        }

        if (this.vehicleYear == null) {
            this.vehicleYear = "";
        }

        if (this.drivingDistance == null) {
            this.drivingDistance = 0;
        }

        if (this.addressDetail == null) {
            this.addressDetail = "";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMyPageInfo(
            String name,
            LocalDate birthDate,
            String gender,
            String phone,
            String address,
            String addressDetail,
            String email,
            String hasVehicle,
            String vehicleModel,
            String vehicleYear,
            Integer drivingDistance
    ) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.addressDetail = addressDetail;
        this.email = email;
        this.vehicleModel = vehicleModel == null ? "" : vehicleModel;
        this.vehicleYear = vehicleYear == null ? "" : vehicleYear;
        this.drivingDistance = drivingDistance == null ? 0 : drivingDistance;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void withdraw() {
        this.userStatus = "WITHDRAWN";
        this.withdrawnAt = LocalDateTime.now();
    }
}