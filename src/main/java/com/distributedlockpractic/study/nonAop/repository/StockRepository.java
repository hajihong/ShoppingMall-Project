package com.distributedlockpractic.study.nonAop.repository;

import com.distributedlockpractic.study.nonAop.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock getByProductId(Long productId);
}