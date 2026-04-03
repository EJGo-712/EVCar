package com.evcar.service.chatbot;

import com.evcar.dto.admin.AdminDashboardResponseDto;

public interface FastApiDashboardAnalysisService {

    String analyzeDashboard(AdminDashboardResponseDto dashboardResponseDto);
}