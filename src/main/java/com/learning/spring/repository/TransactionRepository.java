package com.learning.spring.repository;

import com.learning.spring.domain.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByType(String type);
    List<TransactionEntity> findByParentId(Long parentId);
}