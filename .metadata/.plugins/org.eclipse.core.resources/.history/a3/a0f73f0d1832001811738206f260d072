package osdi.collections;

import osdi.locks.Semaphore;
import osdi.locks.SpinLock;

public class OrderBoundTrafficBufferImpl<T> implements OrderQueue<T> {
    private final int bufferSize;
    private final java.util.Queue<T> queue;
    private final Semaphore full;
    private final Semaphore empty;
    private final SpinLock lock;

    public OrderBoundTrafficBufferImpl(int bufferSize) {
        this.bufferSize = bufferSize;
        queue = new java.util.ArrayDeque<>();
        full = new Semaphore(0);
        empty = new Semaphore(bufferSize);
        lock = new SpinLock();
    }

    @Override
    public void enqueue(T item) {
        empty.down();
        try {
            lock.lock();
            queue.add(item);
        } finally {
            lock.unlock();
        }
        full.up();
    }

    @Override
    public T dequeue() {
        T item;
        full.down();
        try {
            lock.lock();
            item = queue.remove();
        } finally {
            lock.unlock();
        }
        empty.up();
        return item;
    }

    @Override
    public boolean isEmpty() {
        try {
            lock.lock();
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}
