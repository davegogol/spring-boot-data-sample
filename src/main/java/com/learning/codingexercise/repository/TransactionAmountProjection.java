package com.learning.codingexercise.repository;

import com.learning.codingexercise.domain.entity.Transaction;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { Transaction.class })
public interface TransactionAmountProjection {
    Double getAmount();
}