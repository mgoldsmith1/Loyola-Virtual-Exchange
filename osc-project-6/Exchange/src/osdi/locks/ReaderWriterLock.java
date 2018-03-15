package osdi.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * Grad students only - re-write this in terms of osdi.locks.SpinLock
 */
/*
 * Grad students only - you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class ReaderWriterLock {
    private final java.util.concurrent.locks.ReadWriteLock javaRwLock = new ReentrantReadWriteLock(false);
  //  private int readWait = 0, writeWait = 0;
   // private int activeRead = 0, activeWrite = 0;

    public void EnterWrite() {
        javaRwLock.writeLock().lock();
    }

    public void ExitWrite() {
        javaRwLock.writeLock().unlock();
    }
    
    public void EnterRead() {
        javaRwLock.readLock().lock();
    }

    public void ExitRead() {
        javaRwLock.readLock().unlock();
    }

    
}
