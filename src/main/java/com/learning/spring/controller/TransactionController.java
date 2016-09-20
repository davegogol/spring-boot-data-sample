package com.learning.spring.controller;

import com.learning.spring.domain.web.TransactionRequest;
import com.learning.spring.domain.entity.TransactionEntity;
import com.learning.spring.repository.TransactionRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionRepository transactionRepository;

    @PutMapping( value = "/transaction/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putTransaction(@PathVariable("id") long id, @RequestBody TransactionRequest transactionRequest) {
        LOGGER.info("PUT /transaction/{id} >> Id: {}, type:  {}", id, transactionRequest.getType());
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(id);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setParentId(transactionRequest.getParentId());
        transactionRepository.save(transaction);
    }

    @GetMapping( value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionEntity getTransactionById(@PathVariable("id") long id) {
        LOGGER.info("GET /transaction/{id} >> Id:  {}", id);
        TransactionEntity transaction = transactionRepository.findOne(id);
        return transaction;
    }

    @GetMapping( value = "/types/{types}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long>  getTransactionByType(@PathVariable("types") String type) {
        LOGGER.info("GET /types/{types} >> type:  {}", type);
        List<Long> transactionEntityList =
                transactionRepository.
                        findByType(type).
                        stream().
                        map(TransactionEntity::getId).
                        collect(Collectors.toList());
        return transactionEntityList;
    }

    @GetMapping( value = "/sum/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Double getSumByParentTransactionId(@PathVariable("transaction_id") Long transactionId) {
        LOGGER.info("GET /sum/{transaction_id} >> transaction_id:  {}", transactionId);
        return transactionRepository.
                findByParentId(transactionId).
                stream().
                mapToDouble(TransactionEntity::getAmount).
                sum();
    }
}