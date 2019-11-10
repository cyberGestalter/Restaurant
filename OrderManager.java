/*
package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

//Управление очередью заказов
public class OrderManager */
/*implements Observer*//*
 {
    //Очередь заказов


    public OrderManager() {

        //orderQueue  = new LinkedBlockingQueue<>();
        //каждые 10 миллисекунд проверять очередь. Если в очереди есть заказы,
        // то найти свободных поваров и передать им заказы (метод startCookingOrder),
        // если нет свободных поваров или нет заказов в очереди, то ждать дальше.
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {}
                    if (!orderQueue.isEmpty()) {
                        Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                        for (Cook cook : cooks) {
                            if (!cook.isBusy()) {
                                Order order = orderQueue.poll();
                                if (order != null)
                                    cook.startCookingOrder(order);
                            }
                            if (orderQueue.isEmpty())
                                break;
                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    */
/*@Override
    public void update(Observable tablet, Object order) {
        try {
            orderQueue.put((Order) order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*//*


    public LinkedBlockingQueue<Order> getOrderQueue() {
        return orderQueue;
    }
}
*/
