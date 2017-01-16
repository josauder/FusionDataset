package de.kdld16.hpi.resolver;

import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.util.rdfdatatypecomparison.RDFDatatypeWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonathan on 09.12.16.
 */
public class Mode<T> implements Resolver {

    public Mode(RDFDatatypeWrapper<T> rdfDatatypeWrapper) {
        this.rdfDatatypeWrapper = rdfDatatypeWrapper;
        this.weightFunction= new StandardWeightFunction();
    }

    public Mode(WeightFunction w, RDFDatatypeWrapper<T> rdfDatatypeWrapper) {
        this.rdfDatatypeWrapper = rdfDatatypeWrapper;
        this.weightFunction=w;
    }

    RDFDatatypeWrapper<T> rdfDatatypeWrapper;
    WeightFunction weightFunction;
    HashMap<T,ArrayList<String>> map;

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
        return new ResolveResult(rdfDatatypeWrapper.representValue(max.getKey()), max.getValue(), n/total);
    }



    /**
     * "main" function of Mode which accepts collection that contains conflict, and returns most common value
     * @param conflict
     * @return ResolveResult containing most common value
     */
    @Override
    public ResolveResult resolve(RDFFactCollection conflict) {
        map = new HashMap<>();
        for (RDFFact fact: conflict.asList()) {
            T key = rdfDatatypeWrapper.getKey(map, rdfDatatypeWrapper.interpretValue(fact.getRdfObject()));
            map.get(key).add(fact.getLanguage());
        }
        return getMaximumCount();
    }


}
