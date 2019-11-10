package com.javarush.task.task27.task2712.ad;

import java.util.*;
import java.util.stream.Collectors;

//Предоставляет информацию из AdvertisementStorage в нужном виде
//Синглтон
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {

    }
    //Достает из хранилища AdvertisementStorage список активных рекламных роликов
    //Активным роликом считается тот, у которого есть минимум один доступный показ.
    public TreeMap<String, Integer> activeVideoSet() {
        TreeMap<String,Integer> result = new TreeMap<>();
        List<Advertisement> videosList = storage.list();

        for (Advertisement ad : videosList) {
            String videoName = ad.getName();
            int videoHit = ad.getHits();
            if (videoHit > 0) {
                result.put(videoName, videoHit);
            }
        }

        return result;
    }
    //Достает из хранилища AdvertisementStorage список не активных рекламных роликов
    //Неактивным роликом считается тот, у которого количество показов равно 0.
    public List<String>/*TreeMap<String,Object>*/ archivedVideoSet() {
        /*return storage.list().stream().collect(Collectors.groupingBy(Advertisement::getName,));*/
        List<String> result = new ArrayList<>();
        /*TreeMap<String,Object> result = new TreeMap<>();*/
        List<Advertisement> videosList = storage.list();

        for (Advertisement ad : videosList){
            String videoName = ad.getName();
            int videoHit = ad.getHits();
            if (!(videoHit > 0)) {
                result.add(videoName);
                //result.put(videoName, null);
            }
        }

        Collections.sort(result, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        return result;
    }

    public static StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }
}
