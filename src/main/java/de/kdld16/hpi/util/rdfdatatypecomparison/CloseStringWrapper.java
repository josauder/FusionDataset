package de.kdld16.hpi.util.rdfdatatypecomparison;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonathan on 09.12.16.
 */

/**
 * Use this for comparing Strings where some error should be allowed.
 * Example RDF Properties are:
 *      Object Properties ( Where RDF Object is Wikidata Entity
 *      String properties such as ISO Codes
 */

public class CloseStringWrapper extends RDFDatatypeWrapper<String> {

    @Override
    public String interpretValue(String val) {
        return val;
    }

    @Override
    public boolean sameValue(String a, String b) {
        return a.equals(b);
    }

    @Override
    public String representValue(String x) {
        return x;
    }

    @Override
    public String getKey(Map<String, ArrayList<String>> map, String k) {
        if (!map.containsKey(k)) {
            map.put(k, new ArrayList<>());
        }
        return k;
    }
}
