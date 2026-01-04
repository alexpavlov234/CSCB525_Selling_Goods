package org.nbu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();


        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println("Магазинът отваря врати! Налични касиери: 4\n");

        for (int i = 1; i <= 10; i++) {
            String buyerName = "Купувач #" + i;
            Map<String, Integer> cart = generateRandomCart();

            BuyerTask task = new BuyerTask(buyerName, store, cart);

            executor.submit(task);
        }


        executor.shutdown();

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        store.printInventory();
    }

    private static Map<String, Integer> generateRandomCart() {
        Map<String, Integer> cart = new HashMap<>();
        Random random = new Random();

        if (random.nextBoolean()) cart.put("Хляб", random.nextInt(3) + 1);
        if (random.nextBoolean()) cart.put("Мляко", random.nextInt(2) + 1);
        if (random.nextBoolean()) cart.put("Яйца", random.nextInt(10) + 1);
        if (random.nextBoolean()) cart.put("Шоколад", random.nextInt(5) + 1);

        if (cart.isEmpty()) {
            cart.put("Кафе", 1);
        }

        return cart;
    }
}