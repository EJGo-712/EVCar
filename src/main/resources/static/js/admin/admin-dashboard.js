'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const dashboardElement = document.getElementById('evDashboardData');

    const dashboardData = {
        monthlyLabels: parseStringArray(dashboardElement?.dataset.monthlyLabels),
        monthlyCounts: parseNumberArray(dashboardElement?.dataset.monthlyCounts),
        regionLabels: parseStringArray(dashboardElement?.dataset.regionLabels),
        regionCounts: parseNumberArray(dashboardElement?.dataset.regionCounts)
    };

    renderMonthlyConsultationChart(
        convertMonthLabels(dashboardData.monthlyLabels),
        dashboardData.monthlyCounts
    );

    renderRegionConsultationChart(
        dashboardData.regionLabels,
        dashboardData.regionCounts
    );
});

function parseStringArray(value) {
    if (!value || value.trim() === '') {
        return [];
    }

    return value.split(',').map((item) => item.trim()).filter((item) => item !== '');
}

function parseNumberArray(value) {
    if (!value || value.trim() === '') {
        return [];
    }

    return value.split(',')
        .map((item) => Number(item.trim()))
        .filter((item) => !Number.isNaN(item));
}

function convertMonthLabels(monthKeys) {
    return monthKeys.map((monthKey) => {
        const parts = String(monthKey).split('-');
        if (parts.length < 2) {
            return monthKey;
        }

        return parseInt(parts[1], 10) + '월';
    });
}

function renderMonthlyConsultationChart(labels, counts) {
    const canvas = document.getElementById('monthlyConsultationChart');

    if (!canvas || !labels.length || !counts.length) {
        return;
    }

    const context = canvas.getContext('2d');

    new Chart(context, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: '상담 건수',
                    data: counts,
                    backgroundColor: [
                        'rgba(59, 130, 246, 0.82)',
                        'rgba(14, 165, 233, 0.82)',
                        'rgba(34, 211, 238, 0.82)'
                    ],
                    borderRadius: 12,
                    borderSkipped: false,
                    maxBarThickness: 72
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
                padding: {
                    top: 8,
                    right: 8,
                    bottom: 0,
                    left: 0
                }
            },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    backgroundColor: 'rgba(15, 23, 42, 0.94)',
                    titleColor: '#f8fafc',
                    bodyColor: '#e2e8f0'
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: '#cbd5e1',
                        font: {
                            size: 13,
                            weight: '600'
                        }
                    },
                    grid: {
                        display: false
                    },
                    border: {
                        display: false
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1,
                        color: '#94a3b8',
                        font: {
                            size: 12
                        }
                    },
                    grid: {
                        color: 'rgba(148, 163, 184, 0.12)'
                    },
                    border: {
                        display: false
                    }
                }
            }
        }
    });
}

function renderRegionConsultationChart(labels, counts) {
    const canvas = document.getElementById('regionConsultationChart');

    if (!canvas || !labels.length || !counts.length) {
        return;
    }

    const context = canvas.getContext('2d');

    new Chart(context, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [
                {
                    data: counts,
                    backgroundColor: [
                        '#38bdf8',
                        '#22d3ee',
                        '#60a5fa',
                        '#34d399',
                        '#f59e0b',
                        '#a78bfa',
                        '#f472b6',
                        '#94a3b8'
                    ],
                    borderWidth: 0,
                    hoverOffset: 6
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '64%',
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    backgroundColor: 'rgba(15, 23, 42, 0.94)',
                    titleColor: '#f8fafc',
                    bodyColor: '#e2e8f0'
                }
            }
        }
    });
}