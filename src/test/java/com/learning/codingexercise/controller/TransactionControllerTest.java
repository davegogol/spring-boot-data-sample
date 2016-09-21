package com.learning.codingexercise.controller;

import com.learning.codingexercise.dao.TransactionDao;
import com.learning.codingexercise.domain.entity.Transaction;
import com.learning.codingexercise.domain.web.TransactionRequestAndResponse;
import com.learning.codingexercise.domain.web.SumTransactionsByParentIdResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
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
    private TransactionDao transactionDaoMock;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddTransaction(){
        TransactionRequestAndResponse transactionRequestAndResponse = new TransactionRequestAndResponse();
        transactionRequestAndResponse.setParentId(PARENT_ID);
        transactionRequestAndResponse.setType(TRANSACTION_TYPE);
        transactionRequestAndResponse.setAmount(10.0);
        transactionController.addTransaction(ID, transactionRequestAndResponse);

        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionDaoMock).add(argument.capture());
        assertEquals(10.0, argument.getValue().getAmount());
    }

    @Test
    public void testGetTransactionByTypeReturnOneTransactionEntity(){
        Set<Long> transactionTypeList = new HashSet<>();
        transactionTypeList.add(ID);

        when(transactionDaoMock.getTransactionIdsByType(TRANSACTION_TYPE)).thenReturn(transactionTypeList);

        Set<Long> arrayList = transactionController.getTransactionByType(TRANSACTION_TYPE);

        assertEquals(1, arrayList.size());
    }


    @Test
    public void testGetTransactionByIdReturnOneTransactionEntity(){
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setParentId(PARENT_ID);
        transaction.setType(TRANSACTION_TYPE);
        transaction.setAmount(0.0);

        when(transactionDaoMock.find(ID)).thenReturn(transaction);

        TransactionRequestAndResponse returnedTransaction = transactionController.getTransactionById(0l);

        assertEquals(transaction.getAmount(), returnedTransaction.getAmount());
    }

    @Test
    public void testGetSumByParentTransactionIdReturnDummySum(){
        when(transactionDaoMock.getSumOfTransactionsRelatedByParentId(PARENT_ID)).thenReturn(SUM_TRANSACTION_BY_PARENT_ID_VALUE);

        SumTransactionsByParentIdResponse calculatedSum = transactionController.getSumByParentTransactionId(PARENT_ID);

        assertEquals(60.0, calculatedSum.getSum());
    }
}