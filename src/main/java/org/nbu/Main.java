package org.nbu;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();

        List<BuyerTask> allTasks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String buyerName = "Купувач #" + i;
            Map<String, Integer> cart = generateRandomCart();
            allTasks.add(new BuyerTask(buyerName, store, cart));
        }

        System.out.println("Магазинът отваря врати! Подготвени купувачи: " + allTasks.size());
        System.out.println("Стартиране на обработка с 4 нишки...\n");

        Thread[] threads = new Thread[4];

        Runnable workerLogic = () -> {
            while (true) {
                BuyerTask taskToProcess = null;

                synchronized (allTasks) {
                    if (!allTasks.isEmpty()) {
                        taskToProcess = allTasks.remove(0);
                    }
                }

                if (taskToProcess == null) {
                    break;
                }

                taskToProcess.run();
            }
        };

        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(workerLogic, "Worker-Thread-" + (i + 1));
            threads[i].start();
        }

        for (int i = 0; i < 4; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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