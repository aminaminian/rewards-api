package com.example.apis.record;

import lombok.Builder;

@Builder
public record CustomerResponse(String customerId, String fullName, String totalPoints, String currentMonthPoints) {
}
