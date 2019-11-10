package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

//повар, который готовит блюда
public class Cook implements Runnable/*extends Observable*/ /*implements Observer*/ {
    String name;
    //Занят ли повар
    boolean busy;

    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public Cook(String name) {
        this.name = name;
    }

    //@Override //observable - объект, который отправил нам значение, arg - объект Order
    /*public void update(Observable tablet, Object order) {
        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + ((Order) order).getTotalCookingTime()/60 + "min");
        //Регистрация события - повар приготовил заказ
        //statisticManager.register(new CookedOrderEventDataRow(tablet.toString(), name, ((Order) order).getTotalCookingTime(), ((Order) order).getDishes()));
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(tablet.toString(), name, ((Order) order).getTotalCookingTime()*60, ((Order) order).getDishes()));
        Cook.this.setChanged();
        Cook.this.notifyObservers(order);
    }*/

    public void startCookingOrder(Order order){
        this.busy = true;

        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + (/*(Order) */order).getTotalCookingTime()/*/60*/ + "min");

        //Регистрация события - повар приготовил заказ
        //StatisticManager.getInstance().register(new CookedOrderEventDataRow(tablet.toString(), name, ((Order) order).getTotalCookingTime()*60, ((Order) order).getDishes()));
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(), name, ((Order) order).getTotalCookingTime()*60,
                ((Order) order).getDishes()));
        //Cook.this.setChanged();
        //Cook.this.notifyObservers(order);
        try {
            Thread.sleep(order.getTotalCookingTime()*10);
        } catch (InterruptedException e) {

        }
        this.busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void run() {
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
                    if (!queue.isEmpty()) {
                        //Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                        //for (Cook cook : cooks) {
                            if (!isBusy()) {
                                Order order = queue.poll();
                                if (order != null)
                                    startCookingOrder(order);
                            }
                            if (queue.isEmpty())
                                break;
                        //}
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
