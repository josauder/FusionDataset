package de.kdld16.hpi.resolver;

import de.kdld16.hpi.WikidataEntity;
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
     * "main" function of Mode which accepts collection that contains conflict, and returns most common value
     * @param conflict
     * @return ResolveResult containing most common value
     */
    @Override
    public void resolve(RDFFactCollection conflict, WikidataEntity entity) {
        String property = conflict.getOne().getRdfProperty();

        map = new HashMap<>();
        for (RDFFact fact: conflict.asList()) {
            T key = rdfDatatypeWrapper.getKey(map, rdfDatatypeWrapper.interpretValue(fact.getRdfObject()));
            map.get(key).add(fact.getLanguage());
        }


        int total = conflict.size();
        for (Map.Entry<T, ArrayList<String>> e : map.entrySet()) {
            double weight = weightFunction.getWeight(e.getValue());
            entity.assignConfidenceToFact(weight/total,
                    new RDFFact(property,
                                rdfDatatypeWrapper.representValue(e.getKey()),
                                e.getValue()));
        }
    }
}
