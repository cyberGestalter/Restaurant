package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

//Событие - нет ни одного видео-ролика, который можно показать во время приготовления заказа
public class NoAvailableVideoEventDataRow implements EventDataRow{
    //время приготовления заказа в секундах
    int totalDuration;

    //Текущая дата
    Date currentDate;

    public NoAvailableVideoEventDataRow(int totalDuration) {
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.NO_AVAILABLE_VIDEO;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public int getTime() {
        return 0;
    }
}
