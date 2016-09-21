package com.learning.codingexercise.repository;

import com.learning.codingexercise.domain.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<TransactionIdProjection> findByType(String type);
    List<TransactionAmountProjection> findByParentId(Long parentId);
}

