package com.infogain.rewardsservice.service;

import com.infogain.rewardsservice.record.TransactionRequest;
import com.infogain.rewardsservice.record.TransactionResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionResponse> create(TransactionRequest request);

    Optional<TransactionResponse> update(String transactionId, TransactionRequest request);
}
