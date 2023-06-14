package com.example.apis.service.impl;

import com.example.apis.exeption.CustomerNotFoundException;
import com.example.apis.helper.TransactionHelper;
import com.example.apis.service.CustomerService;
import com.example.apis.helper.CustomerHelper;
import com.example.apis.model.Customer;
import com.example.apis.model.Transaction;
import com.example.apis.record.CustomerResponse;
import com.example.apis.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerResponse getCustomerRewardsInfo(String customerId) {
        int MAX_DURATION_LIMIT_THREE_MONTHS = 3;
        Customer customer = repository.findById(Long.parseLong(customerId)).orElseThrow(CustomerNotFoundException::new);
        log.info("Customer rewards info:::" + customer);
        LocalDate currentDateMinus3Months = LocalDate.now().minusMonths(MAX_DURATION_LIMIT_THREE_MONTHS);
        var transactions = TransactionHelper.getAllTransaction(customerId, currentDateMinus3Months);
        log.info("Valid Transactions:::" + transactions);
        return buildResponse(customer, transactions);
    }

    private CustomerResponse buildResponse(Customer customer, List<Transaction> transactions) {
        return CustomerResponse.builder()
                .customerId(String.valueOf(customer.getCustomerId()))
                .fullName(customer.getFullName().getFirstName() + " " + customer.getFullName().getLastName())
                .totalPoints(CustomerHelper.calculateTotalPointsForThreeMonths(transactions))
                .currentMonthPoints(CustomerHelper.calculateOneMonthPoints(transactions))
                .build();
    }
}
