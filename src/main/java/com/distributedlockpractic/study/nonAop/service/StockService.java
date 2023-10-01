package com.distributedlockpractic.study.nonAop.service;

import com.distributedlockpractic.study.nonAop.entity.Stock;
import com.distributedlockpractic.study.nonAop.repository.StockOptimisticLockRepository;
import com.distributedlockpractic.study.nonAop.repository.StockPessimisticLockRepository;
import com.distributedlockpractic.study.nonAop.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPessimisticLockRepository stockPessimisticLockRepository;
    private final StockOptimisticLockRepository stockOptimisticLockRepository;
    @Transactional
    public void decreaseV1(final Long id, final int quantity) {
        final Stock stock = stockRepository.getByProductId(id);
        stock.decrease(quantity);
    }

    public synchronized void decreaseV2(final Long id, final int quantity) {
        final Stock stock = stockRepository.getByProductId(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Transactional
    public void decreaseV3(final Long productId, final int quantity) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("The productId must be positive number");
        }

        Stock stock = stockPessimisticLockRepository.getByProductId(productId);
        stock.decrease(quantity);
    }

    @Transactional
    public void decreaseV4(final Long productId, final int quantity) {
        final Stock stock = stockOptimisticLockRepository.getByProductId(productId);
        stock.decrease(quantity);
    }
}
