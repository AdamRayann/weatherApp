package org.spring.util;

import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private final int maxRequests;
    private final long intervalMillis;
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private long startTime;

    public RateLimiter(int maxRequestsPerMinute) {
        this.maxRequests = maxRequestsPerMinute;
        this.intervalMillis = 60_000; // 1m
        this.startTime = System.currentTimeMillis();
    }

    public synchronized void acquire() {
        long now = System.currentTimeMillis();

        if (now - startTime >= intervalMillis) {

            startTime = now;
            requestCount.set(0);
        }

        if (requestCount.incrementAndGet() > maxRequests) {
            long sleepTime = intervalMillis - (now - startTime);
            System.out.println("Rate limit reached. Sleeping for " + sleepTime + "ms");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {}
            startTime = System.currentTimeMillis();
            requestCount.set(1);
        }
    }
}

