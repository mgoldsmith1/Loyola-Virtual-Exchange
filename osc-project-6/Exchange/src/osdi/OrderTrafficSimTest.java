package osdi;

import java.time.Duration;
import java.time.Instant;

import osdi.exchange.ExchangeFeed;
import osdi.exchange.NumberRange;

public class OrderTrafficSimTest 
{
    private static final long startValue = 1000L;
    private static final long thousand = 1000L;
    private static final long million = thousand * thousand;
    private static final long endValue = 200L * million;
    
    public static void main( String[] args )
    {
    	 System.out.println("Starting Exchange Feed...");
    	 try {
	 	Thread.sleep(2000);
         } catch (InterruptedException e) {
		// TODO Auto-generated catch block
	        e.printStackTrace();
         }
    	 NumberRange range = new NumberRange(startValue, endValue);
         Instant start = Instant.now();
         long numberOfPrimesInRange = new ExchangeFeed().iterateThroughOrders(range);
         reportNumberOfPrimes(numberOfPrimesInRange);
         Instant end = Instant.now();
         System.out.println("Duration: " + Duration.between(start, end) + " to complete...");    
    }

    private static void reportNumberOfPrimes(long count) {
        System.out.println("There are " + count + " primes in the range");
        System.out.flush();
    }
}
