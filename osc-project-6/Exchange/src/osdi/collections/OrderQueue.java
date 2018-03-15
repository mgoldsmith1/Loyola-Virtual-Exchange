package osdi.collections;

public interface OrderQueue<T> {
    void enqueue(T item);
    T dequeue();
    boolean isEmpty();
}
