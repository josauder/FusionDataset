package de.kdld16.hpi.util;

import java.util.HashMap;

/**
 * Created by jonathan on 16.12.16.
 */
public class CountingHashMap extends HashMap<String,Integer> {

    public CountingHashMap() {
        super();
    }

    public int putOrIncrement(String key) {
        int val = this.containsKey(key) ? this.get(key) +1 : 1;
        this.put(key,val);
        return val;
    }

}
