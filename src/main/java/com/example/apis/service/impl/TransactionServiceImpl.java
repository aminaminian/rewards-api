package com.example.apis.service.impl;

import com.example.apis.exeption.TransactionNotFoundException;
import com.example.apis.service.TransactionService;
import com.example.apis.helper.CustomerHelper;
import com.example.apis.helper.RewardsHelper;
import com.example.apis.model.Transaction;
import com.example.apis.record.TransactionRequest;
import com.example.apis.record.TransactionResponse;
import com.example.apis.repository.TransactionRepository;
import com.example.apis.validation.TransactionValidation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Optional<TransactionResponse> create(TransactionRequest request) {
        log.info("Validating TransactionRequest:::" + request);
        TransactionValidation.validate(request);
        log.info("Valid:::" + request);
        var customerId = CustomerHelper.getCustomerId(request.customerId()).orElseThrow(TransactionNotFoundException::new);
        var transaction = createTransaction(request, customerId);
        return Optional.ofNullable(createResponse(repository.save(transaction)));
    }

    @Override
    @Transactional
    public Optional<TransactionResponse> update(String transactionId, TransactionRequest request) {
        log.info("Validating TransactionRequest:::" + "ID: " + transactionId + " Request: " + request);
        TransactionValidation.validate(request);
        log.info("Valid:::" + "ID: " + transactionId + " Request: " + request);
        var transaction = repository.findById(Long.parseLong(transactionId)).orElseThrow(TransactionNotFoundException::new);
        mapToTransaction(transaction, request);
        return Optional.ofNullable(createResponse(transaction));
    }

    private static void mapToTransaction(Transaction transaction, TransactionRequest request) {
        transaction.setCreatedDate(LocalDate.parse(request.createdDate()));
        transaction.setAmount(new BigDecimal(request.amount()));
        if (transaction.getCustomerId() != Long.parseLong(request.customerId())) {
            transaction.setCustomerId(Long.parseLong(request.customerId()));
        }
    }

    private static TransactionResponse createResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .createdDate(transaction.getCreatedDate().toString())
                .amount(String.valueOf(transaction.getAmount()))
                .point(String.valueOf(transaction.getPoints()))
                .transactionId(String.valueOf(transaction.getTransactionId()))
                .customerId(String.valueOf(transaction.getCustomerId()))
                .build();
    }

    private static Transaction createTransaction(TransactionRequest request, Long customerId) {
        var amount = new BigDecimal(request.amount());
        return Transaction.builder()
                .amount(amount)
                .points(RewardsHelper.calculatePoints(amount))
                .createdDate(LocalDate.parse(request.createdDate()))
                .customerId(customerId)
                .build();
    }
}
