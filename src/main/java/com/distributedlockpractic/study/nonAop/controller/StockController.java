package com.distributedlockpractic.study.nonAop.controller;


import com.distributedlockpractic.study.nonAop.facade.StockLettuceLockFacade;
import com.distributedlockpractic.study.nonAop.facade.StockOptimisticLockFacade;
import com.distributedlockpractic.study.nonAop.facade.StockRedissonLockFacade;
import com.distributedlockpractic.study.nonAop.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class StockController {

    private final StockService stockService;
    private final StockOptimisticLockFacade stockOptimisticLockFacade;
    private final StockLettuceLockFacade stockLettuceLockFacade;
    private final StockRedissonLockFacade stockRedissonLockFacade;

    public StockController(StockService stockService, StockOptimisticLockFacade stockOptimisticLockFacade, StockLettuceLockFacade stockLettuceLockFacade, StockRedissonLockFacade stockRedissonLockFacade) {
        this.stockService = stockService;
        this.stockOptimisticLockFacade = stockOptimisticLockFacade;
        this.stockLettuceLockFacade = stockLettuceLockFacade;
        this.stockRedissonLockFacade = stockRedissonLockFacade;
    }

    @PostMapping("/synchronized")
    public void decrease(@RequestParam("stockId") final Long id, @RequestParam("quantity") final int quantity) {
        System.out.println(id);
        System.out.println(quantity);
        stockService.decreaseV2(id, quantity);
    }

    @PostMapping("/pessimistic")
    public void decreasePessimistic(@RequestParam("productId") final Long productId, @RequestParam("quantity") final int quantity) {
        stockService.decreaseV3(productId, quantity);
    }

    @PostMapping("/optimistic")
    public void decreaseOptimistic(@RequestParam("productId") final Long productId, @RequestParam("quantity") final int quantity) throws InterruptedException {
        stockOptimisticLockFacade.decreaseV4(productId, quantity);
    }

    @PostMapping("/lettuce")
    public void decreaseLettuceLock(@RequestParam("productId") final Long productId, @RequestParam("quantity") final int quantity) throws InterruptedException {
        stockLettuceLockFacade.decreaseV5(productId, quantity);
    }

    @PostMapping("/redisson")
    public void decreaseRedissonLock(@RequestParam("productId") final Long productId, @RequestParam("quantity") final int quantity) throws InterruptedException {
        stockRedissonLockFacade.decreaseV6(productId, quantity);
    }
}
