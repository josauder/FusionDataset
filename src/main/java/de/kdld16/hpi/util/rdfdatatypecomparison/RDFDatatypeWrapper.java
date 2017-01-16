package de.kdld16.hpi.util.rdfdatatypecomparison;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonathan on 15.01.17.
 */
public abstract class RDFDatatypeWrapper<T> {

    String rdfDatatype;

    /**
     * Used for finding key with same value (low distance)
     * @param in
     * @return key where key equals in in terms of defined distance
     */
    public T getKey(Map<T, ArrayList<String>> map, T in) {
        for (Map.Entry<T, ArrayList<String>> e : map.entrySet()) {
            if (sameValue(e.getKey(), in)) {
                return e.getKey();
            }
        }
        map.put(in, new ArrayList<>());
        return in;
    }

    /**
     * Used in inheritance, when attaching datatype.
     * E.g. 123231 -> "123231^^<xsd:double>"
     * @param val
     * @return representable String of value
     */
    public String representValue(T val) {
        return val + "^^"+rdfDatatype;
    }

    /**
     * Used in inheritance, when attaching datatype.
     * E.g. 123231 -> "123231^^<xsd:double>"
     * @param val
     * @return representable String of value
     */
    public abstract T interpretValue(String val);

    public abstract boolean sameValue(T a, T b);
}

