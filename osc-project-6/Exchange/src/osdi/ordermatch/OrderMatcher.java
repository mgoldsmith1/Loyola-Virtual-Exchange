package osdi.ordermatch;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderMatcher {
    private final HashMap<String, Market> markets = new HashMap<>();

    private Market getMarket(String symbol) {
        return markets.computeIfAbsent(symbol, k -> new Market());
    }

    public boolean insert(Order order) {
        return getMarket(order.getSymbol()).insert(order);
    }

    public void match(String symbol, ArrayList<Order> orders) {
        getMarket(symbol).match(symbol, orders);
    }

    public Order find(String symbol, char side, String id) {
        return getMarket(symbol).find(symbol, side, id);
    }

    public void erase(Order order) {
        getMarket(order.getSymbol()).erase(order);
    }

    public void display() {
        for (String symbol : markets.keySet()) {
            System.out.println("MARKET: " + symbol);
            display(symbol);
        }
    }

    public void display(String symbol) {
        getMarket(symbol).display();
    }

}
