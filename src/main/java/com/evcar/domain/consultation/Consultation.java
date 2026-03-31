package com.evcar.domain.consultation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Consultation {

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_CANCELED = "CANCELED";

    @Id
    @Column(name = "consult_id", nullable = false, length = 20)
    private String consultId;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "vehicle_id", nullable = false, length = 20)
    private String vehicleId;

    @Column(name = "preferred_datetime", length = 20)
    private String preferredDatetime;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "purchase_plan", length = 20)
    private String purchasePlan;

    @Column(name = "consult_content", columnDefinition = "TEXT")
    private String consultContent;

    @Column(name = "consult_status", nullable = false, length = 20)
    private String consultStatus;

    @Column(name = "consult_result", length = 20)
    private String consultResult;

    @Column(name = "admin_reply", columnDefinition = "TEXT")
    private String adminReply;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
        if (this.consultStatus == null || this.consultStatus.isBlank()) {
            this.consultStatus = STATUS_PENDING;
        }
    }

    // 어드민용 메서드
    public void updateConsultStatus(String consultStatus) {
        this.consultStatus = consultStatus;
        this.updatedAt = LocalDate.now();
    }

    public void updateReply(String consultResult, String adminReply) {
        this.consultResult = consultResult;
        this.adminReply = adminReply;
        this.updatedAt = LocalDate.now();
    }

    public void updateAdminProcess(String consultStatus, String consultResult, String adminReply) {
        this.consultStatus = consultStatus;
        this.consultResult = consultResult;
        this.adminReply = adminReply;
        this.updatedAt = LocalDate.now();
    }

    // 마이페이지용 메서드
    public boolean canBeCanceled() {
        return STATUS_PENDING.equals(this.consultStatus) || STATUS_IN_PROGRESS.equals(this.consultStatus);
    }

    public void cancel() {
        if (!canBeCanceled()) {
            throw new IllegalArgumentException("취소할 수 없는 상담 상태입니다.");
        }
        this.consultStatus = STATUS_CANCELED;
    }
}