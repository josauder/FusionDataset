package de.kdld16.hpi.resolver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public class ModeResolver extends Resolver {
    /*
        Counts most common element, returns it.
        Only one element in output.
     */

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {
        HashMap<String,Integer> counter= new HashMap<>();


        String mostCommon="";
        int mostCommonN=0;
        int countTriples = 0;
        for (String rdfObject: conflict) {
            countTriples++;
            if (counter.containsKey(rdfObject)) {
                int i = counter.get(rdfObject);
                if (i>=mostCommonN) {
                    mostCommonN++;
                    mostCommon=rdfObject;
                }
                counter.put(rdfObject,i+1);
            } else {
                counter.put(rdfObject,1);
                if (1>=mostCommonN) {
                    mostCommonN++;
                    mostCommon=rdfObject;
                }
            }
        }
        conflict.clear();
        logger.info("Resolved with "+((float)mostCommonN*100)/countTriples+"% ("+mostCommonN+"/"+countTriples+") for property: "+property);
        conflict.add(mostCommon);

        return conflict;


    }
}
