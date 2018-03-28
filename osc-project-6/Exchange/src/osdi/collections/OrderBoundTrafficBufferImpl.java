package osdi.collections;

import osdi.locks.Semaphore;
import osdi.locks.SpinLock;

public class OrderBoundTrafficBufferImpl<T> implements OrderQueue<T> {
    private final int bufferSize;
    private final java.util.Queue<T> queue;
    private final Semaphore full;
    private final Semaphore empty;
    private final SpinLock spinLock;

    public OrderBoundTrafficBufferImpl(int bufferSize) {
        this.bufferSize = bufferSize;
        queue = new java.util.ArrayDeque<>();
        full = new Semaphore(0);
        empty = new Semaphore(bufferSize);
        spinLock = new SpinLock();
    }

    @Override
    public void enqueue(T item) {
        empty.down();
        try {
            spinLock.lock();
            queue.add(item);
        } finally {
            spinLock.unlock();
        }
        full.up();
    }

    @Override
    public T dequeue() {
        T item;
        full.down();
        try {
            spinLock.lock();
            item = queue.remove();
        } finally {
            spinLock.unlock();
        }
        empty.up();
        return item;
    }

    @Override
    public boolean isEmpty() {
        try {
            spinLock.lock();
            return queue.isEmpty();
        } finally {
            spinLock.unlock();
        }
    }
}
