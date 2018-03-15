package osdi.collections;


public class OrderBoundTrafficBuffer {
    public static <T> OrderQueue<T> createOrderTrafficBufferWithSemaphores(int bufferSize) {
        return new OrderBoundTrafficBufferImpl<>(bufferSize);
    }
}

