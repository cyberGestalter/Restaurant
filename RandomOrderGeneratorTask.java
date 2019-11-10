package com.javarush.task.task27.task2712;

import java.util.List;
import java.util.Random;

public class RandomOrderGeneratorTask implements Runnable{
    List<Tablet> tablets;
    int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        if (tablets.size()>0) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tablets.get(new Random().nextInt(tablets.size()-1)/*(int) (Math.random()*tablets.size())*/).createTestOrder();
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    return;
                    //e.printStackTrace();
                }
            }
        }
    }
}

