package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by jonathan on 08.12.16.
 */
public class Mode extends AbstractMode<String> {

    @Override
    public String representValue(String val) {
       return val;
    }

    @Override
    public String getKey(String k) {
        if (!map.containsKey(k)) {
            map.put(k, new ArrayList<>());
        }
        return k;
    }

    @Override
    public String interpretValue(String val) {
        return val;
    }

       //     logger.debug("Resolved with "+((float)mostCommonN*100)/countTriples+"% ("+mostCommonN+"/"+countTriples+") for property: "+property)
}
