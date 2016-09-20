package com.learning.spring.dao;


import com.learning.spring.domain.entity.Transaction;

import java.util.List;

public interface TransactionDao {
    public void add(Transaction entity);
    public Transaction find(Long id);
    public List<Long> getTransactionsByType(String type);
    public double getSumOfTransactionsRelatedByParentId(Long parentId);
}
