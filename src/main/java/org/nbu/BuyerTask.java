package org.nbu;

import java.util.Map;

public class BuyerTask implements Runnable {
    private final String buyerName;
    private final Store store;
    private final Map<String, Integer> shoppingCart;

    public BuyerTask(String buyerName, Store store, Map<String, Integer> shoppingCart) {
        this.buyerName = buyerName;
        this.store = store;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void run() {
        store.sellItems(buyerName, shoppingCart);
    }
}