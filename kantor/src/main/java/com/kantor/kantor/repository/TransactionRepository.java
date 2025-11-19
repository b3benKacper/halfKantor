package com.kantor.kantor.repository;

import com.kantor.kantor.model.Transaction;
import com.kantor.kantor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}