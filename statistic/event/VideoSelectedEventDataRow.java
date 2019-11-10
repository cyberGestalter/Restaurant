package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.ad.Advertisement;

import java.util.Date;
import java.util.List;

//Событие - выбрали набор видео-роликов для заказа
public class VideoSelectedEventDataRow implements EventDataRow{
    //список видео-роликов, отобранных для показа
    private final List<Advertisement> optimalVideoSet;
    //amount - сумма денег в копейках
    private final long amount;
    //totalDuration - общая продолжительность показа отобранных рекламных роликов
    private final int totalDuration;

    //Текущая дата
    Date currentDate;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }

    public long getAmount() {
        return amount;
    }
}
