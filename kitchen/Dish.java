package com.javarush.task.task27.task2712.kitchen;


import java.util.Arrays;

public enum Dish {
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    //хуй знает, кажись в минутах
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    //Динамически формирует строку из блюд
    public static String allDishesToString() {
        //StringBuilder string = new StringBuilder(Dish.values().toString());
        return Arrays.toString(Dish.values());
    }


}
