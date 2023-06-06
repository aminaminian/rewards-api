package com.infogain.rewardsservice.helper;

import com.infogain.rewardsservice.model.Customer;
import com.infogain.rewardsservice.model.Transaction;
import com.infogain.rewardsservice.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CustomerHelper {

    private static final int FIRST_DAY_OF_THE_MONTH = 1;

    private static CustomerRepository repository;

    public CustomerHelper(CustomerRepository repository) {
        CustomerHelper.repository = repository;
    }

    public static Optional<Long> getCustomerId(String customerId) {
        return repository.findById(Long.parseLong(customerId)).map(Customer::getCustomerId);
    }

    public static String calculateTotalPointsForThreeMonths(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getPoints)
                .reduce(Integer::sum)
                .map(String::valueOf)
                .orElse("0");
    }

    public static String calculateOneMonthPoints(List<Transaction> transactions) {
        Predicate<Transaction> FILTER_TRANSACTIONS_FROM_BEGINNING_OF_THE_MONTH =
                t -> !LocalDate.now().withDayOfMonth(FIRST_DAY_OF_THE_MONTH).isAfter(t.getCreatedDate());

        return transactions.stream()
                .filter(FILTER_TRANSACTIONS_FROM_BEGINNING_OF_THE_MONTH)
                .map(Transaction::getPoints)
                .reduce(Integer::sum)
                .map(String::valueOf)
                .orElse("0");
    }
}
