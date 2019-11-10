package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//подбирает оптимальный набор роликов и их последовательность для каждого заказа
public class AdvertisementManager {
    private final AdvertisementStorage storage;
    //private final StatisticManager statisticManager;
    private int timeSeconds;

    private List<List<Advertisement>> bigList;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
        storage = AdvertisementStorage.getInstance();
        //statisticManager = StatisticManager.getInstance();
    }

    public void processVideos() throws NoVideoAvailableException{
        List<Advertisement> availableVideos = storage.list();

        //Проверка списка на пустоту
        if (availableVideos.isEmpty()) throw new NoVideoAvailableException();
        //else StatisticManager.getInstance().register(new VideoSelectedEventDataRow(availableVideos, sumAmountForVideos(availableVideos), sumTimeOfVideos(availableVideos)));

        //Получение списка видео, длительность которых меньше времени приготовления заказа и число показов которых положительное
        List<Advertisement> videoForShow = new ArrayList<>();
        for (Advertisement ad : availableVideos) {
            /*if (ad.getHits()>0) {
                if (ad.getDuration() <= timeSeconds) {
                    videoForShow.add(ad);
                }
            }*/
            if ((ad.getDuration() <= timeSeconds) && (ad.getHits() > 0))
                videoForShow.add(ad);
        }
        //Проверка списка на пустоту
        if (videoForShow.isEmpty()) throw new NoVideoAvailableException();

        allPossibleVideosLists(videoForShow);
        //Проверка списка на пустоту
        if (bigList.isEmpty()) throw new NoVideoAvailableException();


        //Упорядочивание bigList по максимальной сумме, максимальному времени или минимальному количеству роликов
        Collections.sort(bigList, new Comparator<List<Advertisement>>() {
            @Override
            public int compare(List<Advertisement> o1, List<Advertisement> o2) {
                int result = 0 - Long.compare(sumAmountForVideos(o1), sumAmountForVideos(o2));
                if (result == 0) {
                    result = 0 - Integer.compare(sumTimeOfVideos(o1), sumTimeOfVideos(o2));
                    if (result == 0) {
                        result = o1.size() - o2.size();
                    }
                }
                return result;
            }
        });

        //Получение наиболее оптимального (по условию) списка - с максимальной суммой, максимальным временем или минимальным количеством роликов
        List<Advertisement> optimalVideosList = bigList.get(0);

        //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalVideosList, sumAmountForVideos(optimalVideosList), sumTimeOfVideos(optimalVideosList)));
        //Упорядочивание списка по уменьшению суммы за ролик или увеличению стоимости показа одной секунды ролика
        Collections.sort(optimalVideosList, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int result = 0 - Long.compare(o1.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
                if (result == 0)
                    result = Long.compare(o1.getAmountPerOneDisplaying()/o1.getDuration(), o2.getAmountPerOneDisplaying()/o2.getDuration());
                return result;
            }
        });

        //Регистрация события - выбрали набор видео-роликов для заказа
        //statisticManager.register(new VideoSelectedEventDataRow(optimalVideosList, sumAmountForVideos(optimalVideosList), sumTimeOfVideos(optimalVideosList)));
        //if (!optimalVideosList.isEmpty())
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalVideosList, sumAmountForVideos(optimalVideosList), sumTimeOfVideos(optimalVideosList)));
        //else StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));

        //Вывод на экран полученного списка
        for (Advertisement advertisement : optimalVideosList) {
            /*if (advertisement.getHits() > 0)*/
            //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalVideosList, sumAmountForVideos(optimalVideosList), sumTimeOfVideos(optimalVideosList)));
            ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d", advertisement.getName(), advertisement.getAmountPerOneDisplaying(), advertisement.getAmountPerSecond()));
            advertisement.revalidate();
        }
    }

    //Создает список всех возможных вариантов списков видео из полученного списка с общим временем меньше timeSeconds - времени приготовления заказа
    private void allPossibleVideosLists(List<Advertisement> adList) {
        //Обнуление списка возможных вариантов списков видео
        bigList = new ArrayList<List<Advertisement>>();
        for (int i = 1; i < (int) Math.pow(2, adList.size()); i++) {
            List<Advertisement> tempList = new ArrayList<>();
            //Перевод i в двоичный вид
            String iAsBinary = Integer.toBinaryString(i);

            //Добавление в начало iAsBinary нулей для приведения iAsBinary и adList к одинаковой длине
            while (iAsBinary.length() < adList.size()) {
                iAsBinary = "0" + iAsBinary;
            }
            //Добавление во временный список элементов adList, соответствующих единицам в iAsBinary
            for (int j = 0; j < iAsBinary.length(); j++) {
                if (iAsBinary.charAt(j) == '1') {
                    tempList.add(adList.get(j));
                }
            }
            //Добавление в bigList списков видео с суммарным временем меньше времени создания заказа
            if (sumTimeOfVideos(tempList) <= timeSeconds) {
                bigList.add(tempList);
            }
        }
    }

    //Суммарная длительность роликов из списка видео в секундах
    private int sumTimeOfVideos(List<Advertisement> adList) {
        int sumTime = 0;
        for (Advertisement ad : adList) {
            sumTime += ad.getDuration();
        }
        return sumTime;
    }

    //Суммарная стоимость просмотра роликов из списка видео
    private long sumAmountForVideos(List<Advertisement> adList) {
        long sumAmount = 0;
        for (Advertisement ad : adList) {
            sumAmount += ad.getAmountPerOneDisplaying();
        }
        return sumAmount;
    }

    //Сравнение двух списков видео по оптимальности - по сумме денег/по суммарной длительности роликов/количеству роликов
    /*private List<Advertisement> checkWhatMoreOptimize(List<Advertisement> listForCheck1, List<Advertisement> listForCheck2) {
        List<Advertisement> resultList = new ArrayList<>();
        if (sumAmountForVideos(listForCheck1) > sumAmountForVideos(listForCheck2)) {
            resultList = listForCheck1;
        } else if (sumAmountForVideos(listForCheck1) < sumAmountForVideos(listForCheck2)) {
                    resultList = listForCheck2;
                } else if (sumTimeOfVideos(listForCheck1) > sumTimeOfVideos(listForCheck2)) {
                            resultList = listForCheck1;
                        } else if (sumTimeOfVideos(listForCheck1) < sumTimeOfVideos(listForCheck2)) {
                                    resultList = listForCheck2;
                                } else if (listForCheck1.size() < listForCheck2.size()) {
                                                resultList = listForCheck1;
                                        } else resultList = listForCheck2;
        return resultList;
    }*/
}
