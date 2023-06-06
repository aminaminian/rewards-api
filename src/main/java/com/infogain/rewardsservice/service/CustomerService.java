package com.infogain.rewardsservice.service;

import com.infogain.rewardsservice.record.CustomerResponse;

public interface CustomerService {
    CustomerResponse getCustomerRewardsInfo(String customerId);
}
