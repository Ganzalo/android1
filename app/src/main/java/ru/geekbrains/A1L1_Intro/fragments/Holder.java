package ru.geekbrains.A1L1_Intro.fragments;

import java.util.HashMap;
import java.util.Map;

public class Holder {

    private static Map<String, Entry> holderMap = new HashMap<>();

    private Holder() {
    }

    static void put(String key, Entry value) {
        System.out.println("put " + key);
        holderMap.put(key, value);
    }

    static Entry get(String key) {
        System.out.println("get " + key);
        return holderMap.get(key);
    }

    static boolean contains(String key) {
        return holderMap.containsKey(key);
    }

    static class Entry {
        boolean humidity = false;
        boolean overcast = false;

        public Entry() {

        }
    }

}
