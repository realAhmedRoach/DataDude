/**
 * *****************************************************************************
 * DataDude is a data managing application designed to have many types of data
 * in one application Copyright (C) 2015 Ahmed R. (theTechnoKid)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************
 */
package org.datadude.memory;

public class MemoryManager implements Runnable {

    private Runtime r;
    final static long MEGABYTE = 1024L * 1024L;
    final static long KILOBYTE = 1024L;

    public MemoryManager() {
        r = Runtime.getRuntime();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Mem: " + getFreeMemory() + "KB");
            if (getFreeMemory() < 2) {
                System.gc();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return Maximum memory JVM will attempt to use in megabytes.
     */
    public long getMaxMemory() {
        return (r.maxMemory() / MEGABYTE);
    }

    /**
     * @return Maximum memory JVM will attept to use in kilobytes.
     */
    public long getMaxMemoryKB() {
        return (r.maxMemory() / KILOBYTE);
    }

    /**
     * @return The total amount of memory currently available for current and
     * future objects in megabytes
     */
    public long getTotalMemory() {
        return (r.totalMemory() / MEGABYTE);
    }

    /**
     * @return The total amount of memory currently available for current and
     * future objects in kilobytes
     */
    public long getTotalMemoryKB() {
        return (r.totalMemory() / KILOBYTE);
    }

    /**
     * @return The amount of memory used by the JVM in megabytes
     */
    public long getUsedMemory() {
        return ((r.freeMemory() - getTotalMemory()) / MEGABYTE);
    }

    /**
     * @return The amount of memory used by the JVM in kilobytes
     */
    public long getUsedMemoryKB() {
        return ((r.freeMemory() - getTotalMemory()) / KILOBYTE);
    }

    /**
     * @return The amount of free memory on kilobytes
     */
    public long getFreeMemory() {
        return (r.freeMemory() / KILOBYTE);
    }
}
