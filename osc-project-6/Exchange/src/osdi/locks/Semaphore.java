package osdi.locks;

/*
 * Grad students only - re-write this in terms of osdi.locks.SpinLock
 */
/*
 * Grad students only - you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class Semaphore {
    private final java.util.concurrent.Semaphore javaSemaphore;

    public Semaphore(int initialValue) {
        if(initialValue < 0) {
            throw new IllegalStateException("initialValue >= 0");
        }
        javaSemaphore = new java.util.concurrent.Semaphore(initialValue);

    }

    public void down() {
        try {
            javaSemaphore.acquire(1);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public void up() {
        javaSemaphore.release(1);
    }
}
