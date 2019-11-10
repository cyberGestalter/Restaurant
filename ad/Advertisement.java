package com.javarush.task.task27.task2712.ad;

//Рекламное объявление
public class Advertisement {
    //видео
    private Object content;
    //имя/название
    private String name;
    //начальная сумма, стоимость рекламы в копейках
    private long initialAmount;
    //количество оплаченных показов
    private int hits;
    //продолжительность в секундах
    private int duration;
    //стоимость одного показа рекламного объявления в копейках
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        //Для устранения проблемы деления на 0 и неопределенности при выборе роликов в AdvertisementManager.processVideo()
        this.amountPerOneDisplaying = hits > 0 ? initialAmount/hits : 0;
    }

    public void revalidate() {
        if (hits <= 0)
            //throw new UnsupportedOperationException();
            throw new NoVideoAvailableException();
        hits--;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getHits() { return hits; }

    public long getAmountPerSecond()
    {
        // Тысячные доли копейки за секунду
        return amountPerOneDisplaying*1000/duration;
    }
}
