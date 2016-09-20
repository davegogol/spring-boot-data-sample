package com.learning.spring;

import com.learning.spring.controller.TransactionController;
import com.learning.spring.domain.entity.TransactionEntity;
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

public class TransactionControllerTest {

    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final long ID = 0L;
    private static final long PARENT_ID = 90L;
    /**
     * Unit under test
     */
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactionByTypeReturnOneTransactionEntity(){
        TransactionEntity transactionEntity = createTransactionEntity();
        transactionEntity.setAmount(0.0);
        List<TransactionEntity> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionEntity);

        when(transactionRepositoryMock.findByType(TRANSACTION_TYPE)).thenReturn(transactionTypeList);

        List<Long> arrayList = transactionController.getTransactionByType(TRANSACTION_TYPE);

        assertEquals(1, arrayList.size());
    }

    @Test
    public void testGetTransactionByIdReturnOneTransactionEntity(){
        TransactionEntity transactionEntity = createTransactionEntity();
        transactionEntity.setAmount(0.0);

        when(transactionRepositoryMock.findOne(ID)).thenReturn(transactionEntity);

        TransactionEntity returnedTransactionEntity = transactionController.getTransactionById(0l);

        assertEquals(transactionEntity.getId(), returnedTransactionEntity.getId());
    }

    @Test
    public void testGetSumByParentTransactionIdReturnDummySum(){
        TransactionEntity transactionEntity = createTransactionEntity();
        transactionEntity.setAmount(10.0);
        TransactionEntity transactionEntity2 = new TransactionEntity();
        transactionEntity2.setId(1L);
        transactionEntity2.setParentId(PARENT_ID);
        transactionEntity2.setAmount(50.0);
        transactionEntity2.setType(TRANSACTION_TYPE);
        List<TransactionEntity> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionEntity);
        transactionTypeList.add(transactionEntity2);

        when(transactionRepositoryMock.findByParentId(PARENT_ID)).thenReturn(transactionTypeList);

        double calculatedSum = transactionController.getSumByParentTransactionId(PARENT_ID);
        assertEquals(60.0, calculatedSum);
    }

    private TransactionEntity createTransactionEntity() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(ID);
        transactionEntity.setParentId(PARENT_ID);
        transactionEntity.setType(TRANSACTION_TYPE);
        return transactionEntity;
    }


}