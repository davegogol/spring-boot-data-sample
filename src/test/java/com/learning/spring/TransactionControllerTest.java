package com.learning.spring;

import com.learning.spring.controller.TransactionController;
import com.learning.spring.model.Transaction;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TransactionControllerTest {

    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final long PARENT_ID = 0l;
    /**
     * Unit under test
     */
    private TransactionController transactionController = new TransactionController();

    @Test
    public void testGetTransactionByTypeReturnDummyEmptyList(){
        List<Long> arrayList = transactionController.getTransactionByType(TRANSACTION_TYPE);
        assertEquals(0, arrayList.size());
    }

    @Test
    public void testGetTransactionByIdReturnDummyTransaction(){
        Transaction transaction = transactionController.getTransactionById(0l);
        assertEquals(5.0, transaction.getAmount());
    }

    @Test
    public void testGetSumByParentTransactionIdReturnDummySum(){
        double calculatedSum = transactionController.getSumByParentTransactionId(PARENT_ID);
        assertEquals(0.0, calculatedSum);
    }
}