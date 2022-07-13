package com.gascoigne.thomas.transactionmanager.repository;

import com.gascoigne.thomas.transactionmanager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
