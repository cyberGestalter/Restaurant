package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Класс описывающий заказ
public class Order {
    //Планшет
    private final Tablet tablet;
    //список блюд
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        this.dishes = new ArrayList<>();
        initDishes();
        //this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public boolean isEmpty() {
        if (dishes.isEmpty()) return true;
        return false;
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    //Подсчет времени приготовления заказа секундах или минутах - хуй знает, кажись в минутах
    public int getTotalCookingTime() {
        int cookingDuration = 0;
        for (Dish dish : dishes) {
            cookingDuration += dish.getDuration();
        }
        return cookingDuration;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) {
            return "";
        } else {
            return String.format("Your order: %s of %s", Arrays.toString(dishes.toArray()), tablet.toString());
        }
    }
}

