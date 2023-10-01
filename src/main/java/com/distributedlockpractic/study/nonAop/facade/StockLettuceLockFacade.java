package com.distributedlockpractic.study.nonAop.facade;

import com.distributedlockpractic.study.nonAop.repository.StockRedisRepository;
import com.distributedlockpractic.study.nonAop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockLettuceLockFacade {

    private final StockRedisRepository stockRedisRepository;
    private final StockService stockService;

    public void decreaseV5(final Long id, final int quantity) throws InterruptedException {
        while (!stockRedisRepository.lock(id)) {
            Thread.sleep(50);
        }

        try {
            stockService.decreaseV1(id, quantity);
        } finally {
            stockRedisRepository.unlock(id);
        }
    }
}
