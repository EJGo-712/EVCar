package com.evcar.service.admin;

import com.evcar.dto.admin.AdminDashboardResponseDto;
import com.evcar.repository.admin.AdminDashboardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private static final String CONSULT_STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String CONSULT_STATUS_COMPLETED = "COMPLETED";
    private static final int MONTH_STAT_COUNT = 3;
    private static final int TOP_VEHICLE_COUNT = 3;

    private final AdminDashboardQueryRepository adminDashboardQueryRepository;

    @Override
    public AdminDashboardResponseDto getDashboardData() {
        return AdminDashboardResponseDto.builder()
                .todayReservationCount(adminDashboardQueryRepository.countTodayReservation())
                .inProgressCount(adminDashboardQueryRepository.countByConsultStatus(CONSULT_STATUS_IN_PROGRESS))
                .completedCount(adminDashboardQueryRepository.countByConsultStatus(CONSULT_STATUS_COMPLETED))
                .monthlyConsultationStats(adminDashboardQueryRepository.findMonthlyConsultationStats(MONTH_STAT_COUNT))
                .topVehicleStats(adminDashboardQueryRepository.findTopVehicleStats(TOP_VEHICLE_COUNT))
                .regionConsultationStats(adminDashboardQueryRepository.findRegionConsultationStats())
                .build();
    }
}