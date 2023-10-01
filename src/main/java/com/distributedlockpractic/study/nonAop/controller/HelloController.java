package com.distributedlockpractic.study.nonAop.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final MeterRegistry meterRegistry;

    private final Counter getCounter;
    private final Counter postCounter;
    private final Counter putCounter;
    private final Counter deleteCounter;

    public HelloController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.getCounter = this.meterRegistry.counter("my.get.counter");
        this.postCounter = this.meterRegistry.counter("my.post.counter");
        this.putCounter = this.meterRegistry.counter("my.put.counter");
        this.deleteCounter = this.meterRegistry.counter("my.delete.counter");
    }

    @GetMapping
    public String get(@RequestParam(required = false) String name) {
        getCounter.increment();
        return "get, " + (name != null ? name : "") + "!!";
    }

    @PostMapping
    public String post() {
        postCounter.increment();
        return "post!!";
    }

    @PutMapping("/{id}")
    public String put(@PathVariable String id) {
        putCounter.increment();
        return "put, " + id + "!!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        deleteCounter.increment();
        return "delete, " + id + "!!";
    }
}
