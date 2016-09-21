package com.learning.codingexercise.dao.impl;

import com.learning.codingexercise.dao.TransactionDao;
import com.learning.codingexercise.domain.entity.Transaction;
import com.learning.codingexercise.repository.TransactionAmountProjection;
import com.learning.codingexercise.repository.TransactionIdProjection;
import com.learning.codingexercise.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
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
    public Set<Long> getTransactionIdsByType(String type) {

        LOGGER.debug("Find entities by type. Search details: Type: {}", type);

        List<TransactionIdProjection> transactionEntitiesList =
                transactionRepository.
                        findByType(type);

        LOGGER.debug("Results size: {}", transactionEntitiesList.size());

        Set<Long> transactionEntitiesIdsSet =
                transactionEntitiesList.stream().
                        map(TransactionIdProjection::getId).
                        collect(Collectors.toSet());
        return transactionEntitiesIdsSet;
    }

    @Override
    public double getSumOfTransactionsRelatedByParentId(Long parentId) {

        LOGGER.debug("Find entities by parent id. Search details: Parent Id: {}", parentId);

        List<TransactionAmountProjection> transactionEntitiesList =
                transactionRepository.
                        findByParentId(parentId);

        LOGGER.debug("Results size: {}", transactionEntitiesList.size());

        return transactionEntitiesList.
                stream().
                mapToDouble(TransactionAmountProjection::getAmount).
                sum();
    }
}