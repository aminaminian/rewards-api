package com.example.apis.helper;

import com.example.apis.model.Transaction;
import com.example.apis.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TransactionHelper {

    private static TransactionRepository repository;

    public TransactionHelper(TransactionRepository repository) {
        this.repository = repository;
    }

    public static List<Transaction> getAllTransaction(String customerId, LocalDate currentDateMinus3Months) {
        return repository.findAllByCustomerIdAndMinusThreeMonths(Long.valueOf(customerId), currentDateMinus3Months);
    }
}
