package osdi.exchange;

import osdi.collections.OrderBoundTrafficBuffer;
import osdi.collections.OrderQueue;
import osdi.locks.SpinLock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class ExchangeFeed {

    private long currentCount = 0L;
    private volatile long finishedTasks = 0;
    private final SpinLock finishedLock = new SpinLock();

    private static int getThreadCount() {
        return Runtime.getRuntime().availableProcessors() * 4;
    }

    
    private void startThreads(OrderQueue<Long[]> valuesToCheck, OrderQueue<Long[]> valuesThatArePrime) {
        Collection<Thread> threads = new ArrayList<>();
        int threadCount = getThreadCount();
        for(int i = 0; i < threadCount; ++i) {
            Thread t = new Thread(()->enqueueOrderFeed_FindMarket(valuesToCheck, valuesThatArePrime));
            t.setDaemon(true);
            threads.add(t);
        }
        Thread counter = new Thread(()->dequeueOrderFeed_FoundMarket_LoyolaPrimeIndex(valuesThatArePrime));
        threads.add(counter);

        for(Thread t : threads) {
            t.setDaemon(true);
            t.start();
        }
    }

    public long iterateThroughOrders(NumberRange range) {
        OrderQueue<Long[]> valuesToCheck = OrderBoundTrafficBuffer.createOrderTrafficBufferWithSemaphores(100);
        OrderQueue<Long[]> valuesThatArePrime = OrderBoundTrafficBuffer.createOrderTrafficBufferWithSemaphores(50);

        startThreads(valuesToCheck, valuesThatArePrime);

        final int workSize = 1000;
        int index = -1;
        Long[] array = new Long[workSize];
        long taskCount = 0;
        
        for(Long value : range) {
            ++index;
            if(index == workSize) {
            	System.out.println("Working... " + index);
                valuesToCheck.enqueue(array);
                index = 0;
                array = new Long[workSize];
                ++taskCount;
            }
            array[index] = value;
        }
        
        if(index >= 0) {
            Long[] smaller = new Long[index+1];
            System.arraycopy(array, 0, smaller, 0, index+1);
            valuesToCheck.enqueue(smaller);
            ++taskCount;
        }

        while(true) {
            finishedLock.lock();
            if(finishedTasks == taskCount && valuesThatArePrime.isEmpty()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
                break;
            }
            finishedLock.unlock();
            Thread.yield();
        }
        
        return currentCount;
    }

    
    private void enqueueOrderFeed_FindMarket(OrderQueue<Long[]> ordersToCheck, OrderQueue<Long[]> ordersThatAreLoyolaPrimeIndex) {
        while(true) {
            Long[] toCheck = ordersToCheck.dequeue();
            Collection<Long> primeValues = new ArrayList<>();
      
            for(Long value : toCheck) {
            	
            	// This finds prime numbers for now. 
            	// The purpose of this method is to illustrate if we have found a unique order id from the exchange 
            	// In this case, the value that is found to be prime is treated like an ID
                if(FindMarketID.IsPrime(value)) {
                    primeValues.add(value);
                }
               
            }
            ordersThatAreLoyolaPrimeIndex.enqueue(primeValues.toArray(new Long[0]));
            finishedLock.lock();
            ++finishedTasks;
            finishedLock.unlock();
        }
    }

    private void dequeueOrderFeed_FoundMarket_LoyolaPrimeIndex(OrderQueue<Long[]> ordersThatAreLoyolaPrimeIndex) {
        while(true) {
            Long[] primeValues = ordersThatAreLoyolaPrimeIndex.dequeue();
            currentCount += primeValues.length;
            if(currentCount % 1 == 0) {
                System.out.println("have " + currentCount + " Loyola Prime Index (LPX) orders filled");
                System.out.flush();
            }
        }
    }

}
