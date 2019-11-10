package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Dish;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;

    public static int getOrderCreatingInterval() {
        return ORDER_CREATING_INTERVAL;
    }

    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        //Создание поваров
        //Связывание поваров с очередью заказов
        /*Observer*/Cook cook1 = new Cook("Portia");
        cook1.setQueue(orderQueue);
        /*Observer*/Cook cook2 = new Cook("Loboda");
        cook2.setQueue(orderQueue);

        //Запуск нитей-демонов поваров
        Thread cookThread1 = new Thread((Runnable) cook1);
        cookThread1.start();
        Thread cookThread2 = new Thread((Runnable) cook2);
        cookThread2.start();

        //Создание планшетов
        //Связывание очереди заказов с планшетом
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setOrderQueue(orderQueue);
            tablets.add((Tablet) tablet);
        }

        //Создание официанта
        Observer waiter = new Waiter();
        //Связывание официанта с очередью заказов
        //Запуск нити-демона официанта

        //Запуск нити ресторана
        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        //Показ статистики
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
