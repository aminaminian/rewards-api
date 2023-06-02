package com.infogain.rewardsservice.controller;

import com.infogain.rewardsservice.record.TransactionRequest;
import com.infogain.rewardsservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity createTransaction(@RequestBody TransactionRequest transaction) {
        log.info("API Create Transaction:::");
        return ResponseEntity.ok(transactionService.create(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTransaction(@PathVariable("id") String id, @RequestBody TransactionRequest transaction) {
        log.info("API Update Transaction:::");
        return ResponseEntity.ok(transactionService.update(id, transaction));
    }
}
