package com.infogain.rewardsservice.service;

import com.infogain.rewardsservice.exeption.CustomerNotFoundException;
import com.infogain.rewardsservice.helper.TransactionHelper;
import com.infogain.rewardsservice.model.Customer;
import com.infogain.rewardsservice.model.Transaction;
import com.infogain.rewardsservice.record.CustomerResponse;
import com.infogain.rewardsservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class CustomerService {

    private static int MAX_DURATION_LIMIT_THREE_MONTHS = 3;
    private static int FIRST_DAY_OF_THE_MONTH = 1;

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponse getCustomerRewardsInfo(String customerId) {
        Customer customer = repository.findById(Long.parseLong(customerId)).orElseThrow(() -> new CustomerNotFoundException());
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
                .totalPoints(calculateTotalPointsForThreeMonths(transactions))
                .currentMonthPoints(calculateOneMonthPoints(transactions))
                .build();
    }

    private String calculateTotalPointsForThreeMonths(List<Transaction> transactions) {
        return transactions.stream()
                .map(t -> t.getPoints())
                .reduce(Integer::sum)
                .map(n -> String.valueOf(n))
                .orElse("0");
    }

    private String calculateOneMonthPoints(List<Transaction> transactions) {
        Predicate<Transaction> FILTER_TRANSACTIONS_FROM_BEGINNING_OF_THE_MONTH =
                t -> !LocalDate.now().withDayOfMonth(FIRST_DAY_OF_THE_MONTH).isAfter(t.getCreatedDate());

        return transactions.stream()
                .filter(FILTER_TRANSACTIONS_FROM_BEGINNING_OF_THE_MONTH)
                .map(t -> t.getPoints()).reduce(Integer::sum)
                .map(n -> String.valueOf(n))
                .orElse("0");
    }
}
