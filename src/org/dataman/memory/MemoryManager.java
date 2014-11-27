package org.dataman.memory;

public class MemoryManager implements Runnable{
	
	private Runtime r;
	final static long MEGABYTE = 1024L * 1024L;
	final static long KILOBYTE = 1024L;
	
	public MemoryManager() {
		r = Runtime.getRuntime();
	}
	
	public void run() {
		while(true) {
			System.out.println("Mem: " + getFreeMemory() + "kB");
			//if ()
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public long getMaxMemory() {
		return (r.maxMemory()/MEGABYTE);
	}
	
	public long getTotalMemory() {
		return (r.totalMemory()/MEGABYTE);
	}
	public long getUsedMemory() {
		return ((r.freeMemory() - getTotalMemory())/MEGABYTE);
	}
	public long getFreeMemory() {
		return (r.freeMemory()/KILOBYTE);
	}
}

