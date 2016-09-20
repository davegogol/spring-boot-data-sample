package com.learning.spring.dao.impl;

import com.learning.spring.dao.TransactionDao;
import com.learning.spring.domain.entity.Transaction;
import com.learning.spring.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionDaoImpl implements TransactionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDaoImpl.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void add(Transaction entity) {
        LOGGER.debug("Add new entity. Entity details: Id: {}", entity.getId());
        transactionRepository.save(entity);
    }

    @Override
    public Transaction find(Long id) {
        LOGGER.debug("Find entity. Search details: Id: {}", id);
        Transaction transaction = transactionRepository.findOne(id);
        return transaction;
    }

    @Override
    public List<Long> getTransactionsByType(String type) {

        LOGGER.debug("Find entities by type. Search details: Type: {}", type);

        List<Transaction> transactionEntityList =
                transactionRepository.
                        findByType(type);

        LOGGER.debug("Results size: {}", transactionEntityList.size());

        List<Long> transactionEntityIdsList =  transactionEntityList.stream().
                        map(Transaction::getId).
                        collect(Collectors.toList());

        return transactionEntityIdsList;
    }

    @Override
    public double getSumOfTransactionsRelatedByParentId(Long parentId) {
        return transactionRepository.
                findByParentId(parentId).
                stream().
                mapToDouble(Transaction::getAmount).
                sum();
    }
}