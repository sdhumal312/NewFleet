package com.fleetop.gpsservice.service;

import org.springframework.stereotype.Component;

@Component
public class MemoryStats {

	private Long heapSize;
	private Long heapMaxSize;
	private Long heapFreeSize;
	
	public Long getHeapSize() {
		return heapSize;
	}
	public void setHeapSize(Long heapSize) {
		this.heapSize = heapSize;
	}
	public Long getHeapMaxSize() {
		return heapMaxSize;
	}
	public void setHeapMaxSize(Long heapMaxSize) {
		this.heapMaxSize = heapMaxSize;
	}
	public Long getHeapFreeSize() {
		return heapFreeSize;
	}
	public void setHeapFreeSize(Long heapFreeSize) {
		this.heapFreeSize = heapFreeSize;
	}
}
