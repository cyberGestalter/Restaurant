package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

//Класс планшета для заказа
public class Tablet /*extends Observable*/ {
    //порядковый номер планшета
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public void setOrderQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    //Создание заказа
    public Order createOrder() {
        Order newOrder = null;
        try {
            newOrder = new Order(Tablet.this);
            getOrder(newOrder);
            return newOrder;
        } catch (IOException e) {
            logger.severe("Console is unavailable.");
        }
        return null;
    }

    private void getOrder(Order newOrder) {
        ConsoleHelper.writeMessage(newOrder.toString());
        if (!newOrder.isEmpty()) {
            AdvertisementManager advertisementManager = new AdvertisementManager(newOrder.getTotalCookingTime() * 60);
            try {
                advertisementManager.processVideos();
            } catch (NoVideoAvailableException e) {
                StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(newOrder.getTotalCookingTime()));
                logger.log(Level.INFO, "No video is available for the order " + newOrder);
            }
            //Пометка о том, что состояние планшета изменилось
            //Tablet.this.setChanged();
            //Оповещение об изменении в планшете повара-наблюдателя (передача ему заказа)
            //Tablet.this.notifyObservers(newOrder);

            try {
                queue.put(newOrder);
            } catch (InterruptedException e) {

            }
        }
    }

    //случайным образом генерирует заказ со случайными блюдами не общаясь с реальным человеком.
    //Все необходимые данные передавать в конструкторе
    public void createTestOrder() {
        TestOrder newOrder = null;
        try {
            newOrder = new TestOrder(Tablet.this);
            getOrder(newOrder);
            //return newOrder;
        } catch (IOException e) {
            logger.severe("Console is unavailable.");
        }
        //return null;
    }
        @Override
        public String toString () {
            return String.format("Tablet{number=%d}", this.number);
        }

}
