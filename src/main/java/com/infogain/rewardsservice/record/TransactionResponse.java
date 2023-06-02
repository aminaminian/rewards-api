package com.infogain.rewardsservice.record;

import lombok.Builder;

@Builder
public record TransactionResponse(String createdDate, String amount, String point, String transactionId,
                                  String customerId) {
}
