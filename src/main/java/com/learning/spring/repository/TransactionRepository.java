package com.learning.spring.repository;

import com.learning.spring.domain.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByType(String type);
    List<Transaction> findByParentId(Long parentId);
}