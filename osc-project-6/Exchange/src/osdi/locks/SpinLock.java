package osdi.locks;

import java.util.concurrent.locks.ReentrantLock;

/*
 * Do not modify this code.
 */
public class SpinLock {
    private final ReentrantLock javaLock;

    public SpinLock() {
        javaLock = new ReentrantLock(false);
    }

    public void lock() {
        while(!javaLock.tryLock()){}
    }

    public void unlock() {
        javaLock.unlock();
    }
}