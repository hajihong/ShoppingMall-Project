package com.distributedlockpractic.study.nonAop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class StockRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(final Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(key.toString(), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(final Long key) {
        return redisTemplate.delete(key.toString());
    }
}
