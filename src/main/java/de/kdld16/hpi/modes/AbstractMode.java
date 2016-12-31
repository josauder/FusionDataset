package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonathan on 09.12.16.
 */
public abstract class AbstractMode<T> {


    HashMap<T,ArrayList<String>> map;

    public ModeResult getMaximumCount() {
        int n=0;
        Map.Entry<T,ArrayList<String>> max=null;
        int size;
        int total=0;
        for (Map.Entry<T, ArrayList<String>> e : map.entrySet()) {
            if ((size=e.getValue().size())>n) {
                n=size;
                max=e;
            }
            total+=size;
        }
        return new ModeResult(representValue(max.getKey()), max.getValue(),max.getValue().size(),total);
    }

    public String representValue(T val) {
        return null;
    }

    public ModeResult getMostCommonItem(RDFFactCollection conflict) {
        map = new HashMap<>();
        for (RDFFact fact: conflict.asList()) {
            T key = getKey(interpretValue(fact.getRdfObject()));
            map.get(key).add(fact.getLanguage());
        }
        return getMaximumCount();
    }

    public T interpretValue(String rdfObject) {
        return null;
    }

    public T getKey(T in) {
        return in;
    }

    public boolean sameValue(T a, T b) {
        return a==b;
    }

}
