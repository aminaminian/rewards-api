package com.infogain.rewardsservice.record;

import lombok.Builder;

@Builder
public record CustomerResponse(String customerId, String fullName, String totalPoints, String currentMonthPoints) {
}
