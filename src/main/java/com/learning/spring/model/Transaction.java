package com.learning.spring.model;

import com.fasterxml.jackson.annotation.JsonView;

public class Transaction {
    @JsonView
    private double amount;
    @JsonView
    private String type;
    @JsonView
    private long parentId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}