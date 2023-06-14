package com.example.apis.service.impl;

import com.example.apis.exeption.CustomerNotFoundException;
import com.example.apis.repository.CustomerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CustomerServiceImplTest {

    @Test
    void getCustomerRewardsInfo() {
        CustomerRepository mockRepository = mock(CustomerRepository.class);

        assertThrows(CustomerNotFoundException.class, () -> new CustomerServiceImpl(mockRepository).getCustomerRewardsInfo("1003"));
    }
}