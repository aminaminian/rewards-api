package com.infogain.rewardsservice.service.impl;

import com.infogain.rewardsservice.exeption.CustomerNotFoundException;
import com.infogain.rewardsservice.repository.CustomerRepository;
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