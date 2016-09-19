package com.learning.spring.controller;

import com.learning.spring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @PutMapping( value = "/transaction/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getTransactionById(@PathVariable("id") long id, @RequestBody Transaction transactionRequest) {
        LOGGER.info("PUT /transaction/{id} >> Id: {}, type:  {}", id, transactionRequest.getType());
    }

    @GetMapping( value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction  getTransactionById(@PathVariable("id") long id) {
        LOGGER.info("GET /transaction/{id} >> Id:  {}", id);
        Transaction transaction = new Transaction();
        transaction.setType("transaction_type");
        transaction.setAmount(5.0);
        transaction.setParentId(1L);
        return transaction;
    }

    @GetMapping( value = "/types/{types}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long>  getTransactionByType(@PathVariable("types") String type) {
        LOGGER.info("GET /types/{types} >> type:  {}", type);
        return new ArrayList<>();
    }

    @GetMapping( value = "/sum/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getSumByParentTransactionId(@PathVariable("transaction_id") Long transactionId) {
        LOGGER.info("GET /sum/{transaction_id} >> transaction_id:  {}", transactionId);
        return 0.0;
    }
}