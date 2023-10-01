package com.distributedlockpractic.study.nonAop.repository;

import com.distributedlockpractic.study.nonAop.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface StockOptimisticLockRepository extends JpaRepository<Stock, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Stock getByProductId(Long productId);
}