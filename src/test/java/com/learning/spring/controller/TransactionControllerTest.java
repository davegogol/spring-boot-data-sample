package com.learning.spring.controller;

import com.learning.spring.controller.TransactionController;
import com.learning.spring.dao.TransactionDao;
import com.learning.spring.domain.entity.Transaction;
import com.learning.spring.domain.web.AddTransactionRequest;
import com.learning.spring.domain.web.SumTransactionsByParentIdResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final long ID = 0L;
    private static final long PARENT_ID = 90L;
    private static final double SUM_TRANSACTION_BY_PARENT_ID_VALUE = 60.00;

    /**
     * Unit under test
     */
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionDao transactionDao;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddTransaction(){

        AddTransactionRequest addTransactionRequest = new AddTransactionRequest();
        addTransactionRequest.setParentId(PARENT_ID);
        addTransactionRequest.setType(TRANSACTION_TYPE);
        addTransactionRequest.setAmount(10.0);
        transactionController.putTransaction(ID, addTransactionRequest);

        verify(transactionDao).add(any(Transaction.class));
    }

    @Test
    public void testGetTransactionByTypeReturnOneTransactionEntity(){
        List<Long> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(ID);

        when(transactionDao.getTransactionsByType(TRANSACTION_TYPE)).thenReturn(transactionTypeList);

        List<Long> arrayList = transactionController.getTransactionByType(TRANSACTION_TYPE);

        assertEquals(1, arrayList.size());
    }


    @Test
    public void testGetTransactionByIdReturnOneTransactionEntity(){
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setParentId(PARENT_ID);
        transaction.setType(TRANSACTION_TYPE);
        transaction.setAmount(0.0);

        when(transactionDao.find(ID)).thenReturn(transaction);

        Transaction returnedTransaction = transactionController.getTransactionById(0l);

        assertEquals(transaction.getId(), returnedTransaction.getId());
    }

    @Test
    public void testGetSumByParentTransactionIdReturnDummySum(){

        when(transactionDao.getSumOfTransactionsRelatedByParentId(PARENT_ID)).thenReturn(SUM_TRANSACTION_BY_PARENT_ID_VALUE);

        SumTransactionsByParentIdResponse calculatedSum = transactionController.getSumByParentTransactionId(PARENT_ID);

        assertEquals(60.0, calculatedSum.getSum());
    }
}