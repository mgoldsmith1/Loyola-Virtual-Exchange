package osdi.exchange;

// The purpose of this method is to find a Unique Order ID that is feed from the ExchangeFeed 
// and to determine which Market that ID falls under

public class FindMarketID {
	
	// This finds prime numbers for now. 
	// The purpose of this method is to illustrate if we have found a unique order id from the exchange 
	// In this case, the value that is found to be prime it is treated like an ID
    public static boolean IsPrime(long value) {
        if(value <= 0) throw new IllegalStateException("value > 0");
        if(value <= 2) return true;
        if(value % 2 == 0) return false;
        long end = (long)Math.sqrt(value) + 1;
        for(long divisor = 3; divisor < end; divisor += 2) {
            if(value % divisor == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isISHARESRUSSELL2000(String id) {
    	
      if(id.contains("ISHARES RUSSELL 2000"))
       return true;
      else
	   return false;
   }
   
    /*
     public static boolean isAPPL(string id) {
       if(id =="AAPL")
        return true;
    }
    */
    
    /*
    public static boolean isMSFT(string id) {
      if(id =="MSFT")
       return true;
   }
   */
}