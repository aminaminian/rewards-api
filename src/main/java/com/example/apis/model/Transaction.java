package com.example.apis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "TRANSACTION", indexes = {@Index(name = "IDX_CUSTOMER", columnList = "CUSTOMER_ID")})
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    @NotNull(message = "Transaction id CANNOT be null")
    @Column(name = "TRANSACTION_ID")
    @JsonIgnore
    private Long transactionId;

    @Column(name = "AMOUNT")
    @NotNull(message="Amount CANNOT be null")
    private BigDecimal amount;

    @Column(name = "POINTS")
    @JsonIgnore
    private int points;

    @Column(name = "CREATED_DATE")
    @NotNull(message = "Created date CANNOT be null")
    private LocalDate createdDate;

    @Column(name = "CUSTOMER_ID")
    @NotNull(message = "Customer id CANNOT be null on Transaction table")
    @JsonIgnore
    private Long customerId;
}

