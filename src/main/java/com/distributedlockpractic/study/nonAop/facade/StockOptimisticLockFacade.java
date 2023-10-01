package com.distributedlockpractic.study.nonAop.facade;


import com.distributedlockpractic.study.nonAop.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockOptimisticLockFacade {

    private final StockService optimisticStockService;

    public void decreaseV4(final Long productId, final int quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticStockService.decreaseV4(productId, quantity);
                break;
            } catch (final Exception ex) {
                log.info("##optimistic lock version 충돌");
                Thread.sleep(50);
            }
        }
    }

}
