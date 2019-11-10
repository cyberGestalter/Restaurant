package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

//Интерфейс-маркер для объектов-событий
public interface EventDataRow {
    //Для возвращения типа объекта-события
    EventType getType();

    //Для возвращения даты создания записи int getTime()
    Date getDate();

    //Для возвращения времени - продолжительности
    int getTime();
}
