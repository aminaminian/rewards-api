package com.example.apis.repository;

import com.example.apis.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT c FROM Transaction c WHERE c.customerId = :customerId AND c.createdDate > :currentDateMinus3Months")
    List<Transaction> findAllByCustomerIdAndMinusThreeMonths(Long customerId, LocalDate currentDateMinus3Months);
}
