package com.learning.codingexercise.dao.impl;

import com.learning.codingexercise.domain.entity.Transaction;
import com.learning.codingexercise.repository.TransactionAmountProjection;
import com.learning.codingexercise.repository.TransactionIdProjection;
import com.learning.codingexercise.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        List<TransactionIdProjection> sameTypTransactionsList = new ArrayList<>();
        sameTypTransactionsList.add(new TransactionIdProjection() {
            @Override
            public Long getId() {
                return 5l;
            }
        });
        sameTypTransactionsList.add(new TransactionIdProjection() {
            @Override
            public Long getId() {
                return 0l;
            }
        });

        when(transactionRepositoryMock.findByType(TRANSACTION_TYPE)).thenReturn(sameTypTransactionsList);

        Set<Long> resultedSameTypeTransactionsIdsList =
                transactionDao.getTransactionIdsByType(TRANSACTION_TYPE);

        assertEquals(2, resultedSameTypeTransactionsIdsList.size());
    }

    @Test
    public void testGetSumTransactionsByParentIdReturnsNotZeroSum(){

        List<TransactionAmountProjection> sameParentIdTransactionsList = new ArrayList<>();
        sameParentIdTransactionsList.add(new TransactionAmountProjection() {
            @Override
            public Double getAmount() {
                return 10.00;
            }
        });
        sameParentIdTransactionsList.add(new TransactionAmountProjection() {
            @Override
            public Double getAmount() {
                return 15.00;
            }
        });

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