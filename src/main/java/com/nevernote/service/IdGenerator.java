package com.nevernote.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

/**
 * Unique Id generator for the Application to uniquely identify a resource
 * 
 * @author vandana
 *
 */
@Service
public class IdGenerator {
	private final AtomicLong counter = new AtomicLong(0L);

	public Long getNext() {
		return counter.incrementAndGet();
	}
}