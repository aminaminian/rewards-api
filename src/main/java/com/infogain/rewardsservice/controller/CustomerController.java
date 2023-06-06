package com.infogain.rewardsservice.controller;

import com.infogain.rewardsservice.service.CustomerService;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}/rewardsInfo")
    public ResponseEntity getCustomerRewardsInfo(@NotEmpty @PathVariable("customerId") String customerId) {
        return ResponseEntity.ok(customerService.getCustomerRewardsInfo(customerId));
    }
}
