package com.example.apis.record;

import jakarta.validation.constraints.NotEmpty;

public record TransactionRequest(
        @NotEmpty(message = "CustomerId must NOT be empty") String customerId,
        @NotEmpty(message = "Amount must NOT be empty") String amount,
        @NotEmpty(message = "CreatedDate must NOT be empty") String createdDate) {
}
