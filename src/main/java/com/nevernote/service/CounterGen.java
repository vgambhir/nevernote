package com.nevernote.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class CounterGen {
        private final AtomicLong counter = new AtomicLong();

        public Long getNext()
        {
                return counter.incrementAndGet();
        }
}