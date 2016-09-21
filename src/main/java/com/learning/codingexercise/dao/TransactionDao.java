package com.learning.codingexercise.dao;

import com.learning.codingexercise.domain.entity.Transaction;

import java.util.Set;

public interface TransactionDao {
    public void add(Transaction entity);
    public Transaction find(Long id);
    public Set<Long> getTransactionIdsByType(String type);
    public double getSumOfTransactionsRelatedByParentId(Long parentId);
}
