package com.learning.spring.controller;

import com.learning.spring.dao.TransactionDao;
import com.learning.spring.domain.web.AddTransactionRequest;
import com.learning.spring.domain.entity.Transaction;
import com.learning.spring.domain.web.SumTransactionsByParentIdResponse;
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

import java.util.List;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionDao transactionDao;

    @PutMapping( value = "/transaction/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putTransaction(@PathVariable("id") long id, @RequestBody AddTransactionRequest addTransactionRequest) {
        LOGGER.debug("PUT /transaction/{id} >> Id: {}, type:  {}", id, addTransactionRequest.getType());

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(addTransactionRequest.getAmount());
        transaction.setType(addTransactionRequest.getType());
        transaction.setParentId(addTransactionRequest.getParentId());
        transactionDao.add(transaction);
    }

    @GetMapping( value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction getTransactionById(@PathVariable("id") long id) {
        LOGGER.debug("GET /transaction/{id} >> Id:  {}", id);
        Transaction transaction = transactionDao.find(id);
        return transaction;
    }

    @GetMapping( value = "/types/{types}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> getTransactionByType(@PathVariable("types") String type) {
        LOGGER.debug("GET /types/{types} >> type:  {}", type);

        List<Long> transactionEntityList = transactionDao.getTransactionsByType(type);
        return transactionEntityList;
    }

    @GetMapping( value = "/sum/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SumTransactionsByParentIdResponse getSumByParentTransactionId(@PathVariable("transaction_id") Long transactionId) {
        LOGGER.debug("GET /sum/{transaction_id} >> transaction_id:  {}", transactionId);

        SumTransactionsByParentIdResponse sumTransactionsByParentIdResponse = new SumTransactionsByParentIdResponse();
        sumTransactionsByParentIdResponse.setSum(transactionDao.getSumOfTransactionsRelatedByParentId(transactionId));
        return sumTransactionsByParentIdResponse;

    }
}