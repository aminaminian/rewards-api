package com.infogain.rewardsservice.helper;

import com.infogain.rewardsservice.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerHelper {

    private static CustomerRepository repository;

    public CustomerHelper(CustomerRepository repository) {
        this.repository = repository;
    }

    public static Optional<Long> getCustomerId(String customerId) {
        return repository.findById(Long.parseLong(customerId)).map(c -> c.getCustomerId());
    }
}
