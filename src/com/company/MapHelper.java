package com.company;

import java.util.HashMap;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class MapHelper {

    private HashMap<Object, Object> map;

    MapHelper() {
        map = new HashMap<>();
    }

    MapHelper(HashMap<Object, Object> map) {
        this.map = map;
    }

    int[] getValueByKey(int key) {
        return ((int[])map.get(key));
    }

    double getValueByKeyAndIndex(int key, int index) {
        return ((double[])map.get(key))[index];
    }
}
