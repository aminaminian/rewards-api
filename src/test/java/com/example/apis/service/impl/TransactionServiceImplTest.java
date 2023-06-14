package com.example.apis.service.impl;

import com.example.apis.exeption.TransactionNotFoundException;
import com.example.apis.validation.TransactionValidation;
import com.example.apis.helper.CustomerHelper;
import com.example.apis.helper.RewardsHelper;
import com.example.apis.model.Transaction;
import com.example.apis.record.TransactionRequest;
import com.example.apis.record.TransactionResponse;
import com.example.apis.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    TransactionRepository mockTransactionRepository;

    MockedStatic<TransactionValidation> transactionValidationMockedStatic;
    MockedStatic<CustomerHelper> customerHelperMockedStatic;

    @BeforeEach
    void setUp() {
        mockTransactionRepository = mock(TransactionRepository.class);
        transactionValidationMockedStatic = mockStatic(TransactionValidation.class);
        customerHelperMockedStatic = mockStatic(CustomerHelper.class);
    }

    @AfterEach
    void tearDown() {
        transactionValidationMockedStatic.close();
        customerHelperMockedStatic.close();
    }

    @Test
    void createTransaction_TransactionNotFoundException() {
        var request = new TransactionRequest("1002", "120", "2023-06-05");

        assertThrows(TransactionNotFoundException.class, () -> new TransactionServiceImpl(mockTransactionRepository).create(request));
    }

    @Test
    void createTransaction_Successful() {
        String customerId = "1002";
        var request = new TransactionRequest(customerId, "120", "2023-06-05");
        Transaction transaction = createTransaction(request, Long.parseLong(customerId));
        TransactionResponse response = createResponse(transaction);
        customerHelperMockedStatic.when(() -> CustomerHelper.getCustomerId(request.customerId())).thenReturn(Optional.of(1002L));
        when(mockTransactionRepository.save(any())).thenReturn(transaction);

        Optional<TransactionResponse> transactionResponse = new TransactionServiceImpl(mockTransactionRepository).create(request);

        assertTrue(transactionResponse.isPresent());
        assertEquals(response, transactionResponse.get());
    }

    @Test
    void updateTransaction_TransactionNotFoundException() {
        String transactionId = "1";
        var request = new TransactionRequest("1002", "120", "2023-06-05");

        assertThrows(TransactionNotFoundException.class, () -> new TransactionServiceImpl(mockTransactionRepository).update(transactionId, request));
    }

    @Test
    void updateTransaction_Successful() {
        String transactionId = "1";
        String customerId = "1002";
        var request = new TransactionRequest(customerId, "120", "2023-06-05");
        Transaction transaction = createTransaction(request, Long.parseLong(customerId));
        TransactionResponse response = createResponse(transaction);
        when(mockTransactionRepository.findById(any())).thenReturn(Optional.of(transaction));

        Optional<TransactionResponse> transactionResponse = new TransactionServiceImpl(mockTransactionRepository).update(transactionId, request);

        assertTrue(transactionResponse.isPresent());
        assertEquals(response, transactionResponse.get());
    }

    @Test
    void updateTransaction_CustomerIdNotEqualToCustomerIdInDatabase_Successful() {
        String transactionId = "1";
        String customerId = "1002";
        var request = new TransactionRequest(customerId, "120", "2023-06-05");
        Transaction transaction = createTransaction(request, Long.parseLong(customerId));
        transaction.setCustomerId(1001L);
        when(mockTransactionRepository.findById(any())).thenReturn(Optional.of(transaction));

        Optional<TransactionResponse> transactionResponse = new TransactionServiceImpl(mockTransactionRepository).update(transactionId, request);

        transaction.setCustomerId(1002L);
        TransactionResponse response = createResponse(transaction);
        assertTrue(transactionResponse.isPresent());
        assertEquals(response, transactionResponse.get());
    }

    private Transaction createTransaction(TransactionRequest request, Long customerId) {
        var amount = new BigDecimal(request.amount());
        return Transaction.builder()
                .transactionId(1L)
                .amount(amount)
                .points(RewardsHelper.calculatePoints(amount))
                .createdDate(LocalDate.parse(request.createdDate()))
                .customerId(customerId)
                .build();
    }

    private TransactionResponse createResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .createdDate(transaction.getCreatedDate().toString())
                .amount(String.valueOf(transaction.getAmount()))
                .point(String.valueOf(transaction.getPoints()))
                .transactionId(String.valueOf(transaction.getTransactionId()))
                .customerId(String.valueOf(transaction.getCustomerId()))
                .build();
    }
}