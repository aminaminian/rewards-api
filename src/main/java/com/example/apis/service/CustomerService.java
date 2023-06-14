package com.example.apis.service;

import com.example.apis.record.CustomerResponse;

public interface CustomerService {
    CustomerResponse getCustomerRewardsInfo(String customerId);
}
