package com.example.apis.service;

import com.example.apis.record.TransactionRequest;
import com.example.apis.record.TransactionResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionResponse> create(TransactionRequest request);

    Optional<TransactionResponse> update(String transactionId, TransactionRequest request);
}
