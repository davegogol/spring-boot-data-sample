package com.learning.spring.dao.impl;

import com.learning.spring.domain.entity.Transaction;
import com.learning.spring.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionDaoImplTest{

    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final long ID = 0l;
    private static final long ID2 = 1l;
    private static final long PARENT_ID = 90l;

    /**
     * Unit Under test.
     */
    @InjectMocks
    private TransactionDaoImpl transactionDao;

    @Mock
    TransactionRepository transactionRepositoryMock;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactionsByTypeReturnsSomeTransactions(){
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setAmount(10.0);
        transaction.setType(TRANSACTION_TYPE);
        Transaction transaction2 = new Transaction();

        transaction2.setId(ID2);
        transaction2.setAmount(15.0);
        transaction2.setType(TRANSACTION_TYPE);
        List<Transaction> sameTypTransactionsList = new ArrayList<>();
        sameTypTransactionsList.add(transaction);
        sameTypTransactionsList.add(transaction2);

        when(transactionRepositoryMock.findByType(TRANSACTION_TYPE)).thenReturn(sameTypTransactionsList);

        List<Long> resultedSameTypeTransactionsIdsList =
                transactionDao.getTransactionsByType(TRANSACTION_TYPE);

        assertEquals(2, resultedSameTypeTransactionsIdsList.size());
    }

    @Test
    public void testGetSumTransactionsByParentIdReturnsNotZeroSum(){
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setAmount(10.0);
        transaction.setType(TRANSACTION_TYPE);
        transaction.setParentId(PARENT_ID);
        Transaction transaction2 = new Transaction();
        transaction2.setId(ID);
        transaction2.setAmount(15.0);
        transaction2.setType(TRANSACTION_TYPE);
        transaction2.setParentId(PARENT_ID);
        List<Transaction> sameParentIdTransactionsList = new ArrayList<>();
        sameParentIdTransactionsList.add(transaction);
        sameParentIdTransactionsList.add(transaction2);

        when(transactionRepositoryMock.findByParentId(PARENT_ID)).thenReturn(sameParentIdTransactionsList);

        double sumOfTransactionsRelatedByParentId =
                transactionDao.getSumOfTransactionsRelatedByParentId(PARENT_ID);

        assertEquals(25.00, sumOfTransactionsRelatedByParentId);
    }

    @Test
    public void testGetSumTransactionsByParentIdReturnsZeroSum(){

        when(transactionRepositoryMock.findByParentId(PARENT_ID)).thenReturn(new ArrayList<>());

        double sumOfTransactionsRelatedByParentId =
                transactionDao.getSumOfTransactionsRelatedByParentId(PARENT_ID);

        assertEquals(0.00, sumOfTransactionsRelatedByParentId);
    }


}