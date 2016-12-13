package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by jonathan on 08.12.16.
 */
public class Mode extends AbstractMode {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModeResult resolve(ArrayList<RDFFact> conflict) {
        HashMap<String,Integer> counter= new HashMap<>();
        String property = conflict.get(0).getRdfProperty();
        RDFFact mostCommon=null;
        int mostCommonN=0;
        int countTriples = 0;
        for (RDFFact fact: conflict) {
            String s = fact.getRdfObject();
            countTriples++;
            if (counter.containsKey(s)) {
                int i = counter.get(s);
                if (i>=mostCommonN) {
                    mostCommonN++;
                    mostCommon=fact;
                }
                counter.put(s,i+1);
            } else {
                counter.put(s,1);
                if (1>mostCommonN) {
                    mostCommonN++;
                    mostCommon=fact;
                }
            }
        }
        if (countTriples>1) {
            logger.debug("Resolved with "+((float)mostCommonN*100)/countTriples+"% ("+mostCommonN+"/"+countTriples+") for property: "+property);
        }

        return new ModeResult(mostCommon,mostCommonN,countTriples);


    }
}
