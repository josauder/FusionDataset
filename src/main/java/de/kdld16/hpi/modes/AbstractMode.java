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



    public AbstractMode() {
        weightFunction = new StandardWeightFunction();
    }

    public AbstractMode(WeightFunction w) {
        weightFunction = w;
    }

    HashMap<T,ArrayList<String>> map;
    WeightFunction weightFunction = null;

    /**
     * Finds the Hashmap-key (which is an RDF Value) which is most common by comparing language-list size
     * @return ResolveResult containing value that was contained in the most languages (mode)
     */
    public ResolveResult getMaximumCount() {
        double n=0;
        Map.Entry<T,ArrayList<String>> max=null;
        double weight;
        double total=0;
        for (Map.Entry<T, ArrayList<String>> e : map.entrySet()) {
            if ((weight=weightFunction.getWeight(e.getValue()))>n) {
                n=weight;
                max=e;
            }
            total+=weight;
        }
        return new ResolveResult(representValue(max.getKey()), max.getValue(),max.getValue().size(),(int)total);
    }

    /**
     * Used in inheritance, when attaching datatype.
     * E.g. 123231 -> "123231^^<xsd:double>"
     * @param val
     * @return representable String of value
     */
    public abstract String representValue(T val);

    /**
     * "main" function of Mode which accepts collection that contains conflict, and returns most common value
     * @param conflict
     * @return ResolveResult containing most common value
     */
    public ResolveResult getMostCommonItem(RDFFactCollection conflict) {
        map = new HashMap<>();
        for (RDFFact fact: conflict.asList()) {
            T key = getKey(interpretValue(fact.getRdfObject()));
            map.get(key).add(fact.getLanguage());
        }
        return getMaximumCount();
    }

    /**
     * Used for parsing literals with their appropriate datatype
     * @param rdfObject
     * @return interpreted value
     */
    public abstract T interpretValue(String rdfObject);
    /**
     * Used for finding key with same value (low distance)
     * @param in
     * @return key where key equals in in terms of defined distance
     */
    public T getKey(T in) {
        return in;
    }

}
