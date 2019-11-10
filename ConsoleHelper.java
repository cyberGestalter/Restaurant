package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //для вывода message в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    //для чтения строки с консоли
    public static String readString() throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    //просит пользователя выбрать блюдо и добавляет его в список
    public static List<Dish> getAllDishesForOrder() throws IOException{
        List<Dish> dishesList = new ArrayList<>();
        writeMessage(Dish.allDishesToString());
        writeMessage("Выберите блюдо.");

        //Создание листа блюд, в котором элементы - строки, а не Dish (при использовании Arrays.asList(Dish.values)
        //или символы (при использовании Arrays.asList(Dish.allDishesToString())))
        List<String> dishesInStringList = new ArrayList<>();
        for (Dish dish : Dish.values()) {
            dishesInStringList.add(dish.toString());
        }
        String dish = readString();

        while (!dish.equals("exit")) {
            if (!dishesInStringList.contains(dish)) {
                writeMessage("Такого блюда нет.");
            } else {
                dishesList.add(Dish.valueOf(dish));
            }
            dish = readString();
        }
        return dishesList;
    }
}
