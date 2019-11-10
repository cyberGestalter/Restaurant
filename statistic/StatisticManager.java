package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//Регистратор событий в хранилище событий
//Класс - синглтон
public class StatisticManager {
    private static StatisticManager instance;
    private final StatisticStorage statisticStorage;
    //private Set<Cook> cooks = new HashSet<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    private StatisticManager(){
        statisticStorage = new StatisticStorage();
    }

    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }
        return instance;
    }

    //Регистрация события
    public void register(EventDataRow data) {
        if (data==null) return;
        statisticStorage.put(data);
    }

    //Регистрация повара
    /*public void register(Cook cook) {
        cooks.add(cook);
    }

    public Set<Cook> getCooks() {
        return cooks;
    }*/

    //Достает из хранилища  все данные, относящиеся к отображению рекламы
    //Считает общую прибыль за каждый день
    //Сортирует по убыванию дат
    public TreeMap<String,/*Long*/Double> calculateProfitPerDay(){
        /*return statisticStorage.get(EventType.SELECTED_VIDEOS).stream()
                .collect(Collectors.groupingBy(EventDataRow::getDate, Collectors.summingLong(e -> ((VideoSelectedEventDataRow) e).getAmount())));*/
        TreeMap<String, Double> result = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> listFromStorage = statisticStorage.get(EventType.SELECTED_VIDEOS);
        for (EventDataRow ad : listFromStorage) {
            VideoSelectedEventDataRow adData = (VideoSelectedEventDataRow) ad;
            String date = dateFormat.format(adData.getDate());
            double amount = adData.getAmount()/100.00;
            if (result.containsKey(date)) {
                double tempAmount = result.get(date) + amount;
                result.put(date, tempAmount);
            } else {
                result.put(date, amount);
            }
        }
        return result;
    }

    //Достает все данные,относящиеся к работе повара
    //Cчитает общую продолжительность работы для каждого повара отдельно.
    //Сортирует по убыванию дат
    public TreeMap<String,Map<String,Integer>> calculateCookWorkDuration() {
        TreeMap<String, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> listFromStorage = statisticStorage.get(EventType.COOKED_ORDER);
        for (EventDataRow cookData : listFromStorage) {
            CookedOrderEventDataRow cook = (CookedOrderEventDataRow) cookData;
            String date = dateFormat.format(cook.getDate());
            String name = cook.getCookName();
            Integer cookTime = cook.getTime();/*(int) Math.ceil(cook.getTime());*/
            if (cookTime > 0) {
                Map<String,Integer> cookMapForDate = new TreeMap<>();
                if (!result.containsKey(date)) {
                    //Map<String,Integer> cookMapForDate = new TreeMap<>();
                    cookMapForDate.put(name, cookTime);
                    //result.put(date, cookMapForDate);
                } else {
                    /*Map<String,Integer>*/ cookMapForDate = result.get(date);
                    if (!cookMapForDate.containsKey(name)) {
                        cookMapForDate.put(name,cookTime);
                    } else {
                        int tempTime = cookMapForDate.get(name) + cookTime;
                        cookMapForDate.put(name, tempTime/*cookMapForDate.get(name) + cookTime*/);
                    }
                    //result.put(date, cookMapForDate);
                }
                result.put(date, cookMapForDate);
            }
        }
        return result;
    }

    /*
    //Представляет Date в виде Calendar
    private Calendar yearMonthDay(Date date) {
        return new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay());
    }

    //Сравнивает даты
    private boolean isEqualDates(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }*/


    //Хранилище событий. Доступ к нему только у StatisticManager
    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();
        /*private */StatisticStorage() {
            for (EventType type : EventType.values()) {
                storage.put(type, new ArrayList<EventDataRow>());
            }
        }

        /*public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }*/

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private List<EventDataRow> get(EventType type) {
            return /*getStorage()*/storage.get(type);
        }
    }
}

