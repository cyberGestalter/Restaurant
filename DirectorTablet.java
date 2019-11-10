package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

//Класс для обращения к статистике
public class DirectorTablet {
    //какую сумму заработали на рекламе, сгруппировать по дням
    public void printAdvertisementProfit() {
        TreeMap<String, Double> advertisementProfit = StatisticManager.getInstance().calculateProfitPerDay();
        double totalAmount = 0;
        double amount;
        for (Map.Entry<String, Double> entry : advertisementProfit.entrySet()) {
            amount = entry.getValue();
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"%s - %2$.2f", entry.getKey(), amount));
            totalAmount += amount;
        }
        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"Total - %.2f", totalAmount));
    }
    //загрузка (рабочее время) повара, сгруппировать по дням
    public void printCookWorkloading() {
        Map<String, Map<String, Integer>> cooksWork = StatisticManager.getInstance().calculateCookWorkDuration();
        for (Map.Entry<String, Map<String,Integer>> pair : cooksWork.entrySet()) {
            ConsoleHelper.writeMessage(pair.getKey());
            for (Map.Entry<String,Integer> inPair : pair.getValue().entrySet()) {
                ConsoleHelper.writeMessage(String.format("%s - %d min", inPair.getKey(), inPair.getValue()/60));
            }
            //Пустая строка после вывода всех поваров
            ConsoleHelper.writeMessage("");
        }
    }

    //список активных роликов и оставшееся количество показов по каждому
    public void printActiveVideoSet() {
        TreeMap<String, Integer> activeVideoSet = StatisticAdvertisementManager.getInstance().activeVideoSet();
        for (Map.Entry<String,Integer> pair : activeVideoSet.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s - %d", pair.getKey(), pair.getValue()));
        }
    }

    //список неактивных роликов (с оставшимся количеством показов равным нулю)
    public void printArchivedVideoSet() {
        List<String> archivedVideoSet = StatisticAdvertisementManager.getInstance().archivedVideoSet();
        for (String ad : archivedVideoSet) {
            ConsoleHelper.writeMessage(ad);
        }
        /*TreeMap<String, Object> activeVideoSet = StatisticAdvertisementManager.getInstance().archivedVideoSet();
        for (Map.Entry<String,Object> pair : activeVideoSet.entrySet()) {
            ConsoleHelper.writeMessage(pair.getKey());
        }*/
    }
}
