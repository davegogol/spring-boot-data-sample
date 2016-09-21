package com.learning.codingexercise.controller;

import com.learning.codingexercise.dao.TransactionDao;
import com.learning.codingexercise.domain.web.TransactionRequestAndResponse;
import com.learning.codingexercise.domain.entity.Transaction;
import com.learning.codingexercise.domain.web.SumTransactionsByParentIdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionDao transactionDao;

    @PutMapping( value = "/transaction/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTransaction(@PathVariable("id") long id, @RequestBody TransactionRequestAndResponse transactionRequestAndResponse) {
        LOGGER.info(">> PUT /transaction/{id}");
        LOGGER.debug(">> PUT /transaction/{id}, Id: {}, type: {}", id, transactionRequestAndResponse.getType());
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(transactionRequestAndResponse.getAmount());
        transaction.setType(transactionRequestAndResponse.getType());
        transaction.setParentId(transactionRequestAndResponse.getParentId());
        transactionDao.add(transaction);
    }

    @GetMapping( value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionRequestAndResponse getTransactionById(@PathVariable("id") long id) {
        LOGGER.info(">> GET /transaction/{id}");
        LOGGER.debug(">> GET /transaction/{id}, Id: {}", id);
        Transaction transaction = transactionDao.find(id);
        TransactionRequestAndResponse transactionRequestAndResponse = new TransactionRequestAndResponse();
        transactionRequestAndResponse.setParentId(transaction.getParentId());
        transactionRequestAndResponse.setAmount(transaction.getAmount());
        transactionRequestAndResponse.setType(transaction.getType());
        return transactionRequestAndResponse;
    }

    @GetMapping( value = "/types/{types}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Long> getTransactionByType(@PathVariable("types") String type) {
        LOGGER.info(">> GET /types/{types}");
        LOGGER.debug(">> GET /types/{types} >> type: {}", type);
        Set<Long> transactionEntityList = transactionDao.getTransactionIdsByType(type);
        return transactionEntityList;
    }

    @GetMapping( value = "/sum/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SumTransactionsByParentIdResponse getSumByParentTransactionId(@PathVariable("transaction_id") Long transactionId) {
        LOGGER.info(">> GET /sum/{transaction_id}");
        LOGGER.debug(">> GET /sum/{transaction_id} >> transaction_id: {}", transactionId);
        SumTransactionsByParentIdResponse sumTransactionsByParentIdResponse = new SumTransactionsByParentIdResponse();
        sumTransactionsByParentIdResponse.setSum(transactionDao.getSumOfTransactionsRelatedByParentId(transactionId));
        return sumTransactionsByParentIdResponse;

    }
}