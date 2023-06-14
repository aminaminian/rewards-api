package com.example.apis.controller;

import com.example.apis.record.TransactionRequest;
import com.example.apis.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

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
