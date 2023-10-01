package com.distributedlockpractic.study.nonAop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    @Version
    private Long version;

    public Stock(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(final int quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("재고 부족");
        }

        this.quantity -= quantity;
    }
}