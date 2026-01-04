package org.nbu;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private final Map<String, Integer> inventory = new HashMap<>();

    public Store() {
        inventory.put("Хляб", 50);
        inventory.put("Мляко", 20);
        inventory.put("Яйца", 100);
        inventory.put("Шоколад", 30);
        inventory.put("Кафе", 15);
    }

    public synchronized void sellItems(String buyerName, Map<String, Integer> shoppingCart) {
        System.out.println(buyerName + " започва пазаруване...");


        for (Map.Entry<String, Integer> item : shoppingCart.entrySet()) {
            String product = item.getKey();
            int wantedQuantity = item.getValue();

            if (!inventory.containsKey(product) || inventory.get(product) < wantedQuantity) {
                System.out.println(buyerName + " не успя да купи " + product + " (Няма наличност). Отказ на поръчката.");
                return; // Прекъсваме транзакцията, ако нещо липсва
            }
        }

        for (Map.Entry<String, Integer> item : shoppingCart.entrySet()) {
            String product = item.getKey();
            int wantedQuantity = item.getValue();
            int currentQuantity = inventory.get(product);


            inventory.put(product, currentQuantity - wantedQuantity);
        }

        System.out.println(buyerName + " успешно закупи стоките си!");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printInventory() {
        System.out.println("\n--- КРАЙНИ НАЛИЧНОСТИ В МАГАЗИНА ---");
        inventory.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}