package com.infogain.rewardsservice.service;

import com.infogain.rewardsservice.exeption.CustomerNotFoundException;
import com.infogain.rewardsservice.exeption.TransactionNotFoundException;
import com.infogain.rewardsservice.helper.CustomerHelper;
import com.infogain.rewardsservice.helper.RewardsHelper;
import com.infogain.rewardsservice.model.Transaction;
import com.infogain.rewardsservice.record.TransactionRequest;
import com.infogain.rewardsservice.record.TransactionResponse;
import com.infogain.rewardsservice.repository.TransactionRepository;
import com.infogain.rewardsservice.validation.TransactionValidation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {

    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<TransactionResponse> create(TransactionRequest request) {
        log.info("Validation:::");
        TransactionValidation.validate(request);
        log.info("Validation passed:::");
        var customerId = CustomerHelper.getCustomerId(request.customerId()).orElseThrow(() -> new CustomerNotFoundException());
        var transaction = createTransaction(request, customerId);
        return Optional.ofNullable(createResponse(repository.save(transaction)));
    }

    @Transactional
    public Optional<TransactionResponse> update(String transactionId, TransactionRequest request) {
        log.info("Validation:::");
        TransactionValidation.validate(request);
        log.info("Validation passed:::");
        var transaction = repository.findById(Long.parseLong(transactionId)).orElseThrow(() -> new TransactionNotFoundException());
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
