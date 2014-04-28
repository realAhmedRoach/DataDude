package org.dataman.memory;

public class MemoryManager {
	
	private Runtime r;
	
	public MemoryManager() {
		r = Runtime.getRuntime();
	}
	
	public long getAvailableMemory() {
		return r.totalMemory();
	}
	public long getUsedMemory() {
		return r.freeMemory() - getAvailableMemory();
	}
	public void getFreeMemory() {
		r.freeMemory();
	}
}
