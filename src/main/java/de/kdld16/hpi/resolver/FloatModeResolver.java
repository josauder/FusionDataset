package de.kdld16.hpi.resolver;


import de.kdld16.hpi.util.RDFParseTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by jonathan on 23.11.16.
 */
public class FloatModeResolver extends Resolver {
    /*
        Counts most common element, returns it.
        Elements where the relative numeric value difference is smaller
        than ${resolver.FloatModeResolver.tolerance} as percentage are considered the same element
        Only one element in output.
     */

    // statically initialized to ${resolver.FloatModeResolver.tolerance}
    static double tolerance;


    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {
        HashMap<String,Integer> counter= new HashMap<>();

        class FloatCountPair {
            Double f;
            int count =1;
            public FloatCountPair(Double f) {
                this.f=f;
            }
            public void increment() {
                this.count++;
            }
            public int getCount() {
                return count;
            }
        }


        LinkedList<FloatCountPair> existingFloatValues = new LinkedList<>();

        String[] val = conflict.get(0).replaceAll("\"","").split("\\^\\^",2);
        String firstValue= val[0];
        String datatype = val[1];
        conflict.remove(0);


        FloatCountPair mostCommon= new FloatCountPair(RDFParseTools.parseDouble(datatype,firstValue));
        int mostCommonN=1;
        int countTriples = 0;
        existingFloatValues.add(mostCommon);
        for (String rdfObject : conflict) {
            countTriples++;
            boolean exists = false;
            double value = RDFParseTools.parseDouble(datatype,rdfObject);
            for (FloatCountPair pair : existingFloatValues) {
                logger.debug(""+Math.abs(1-(pair.f/value)));
                if (Math.abs(1-(pair.f/value))<tolerance) {
                    if (pair.getCount()>=mostCommonN) {
                        mostCommon=pair;
                        mostCommonN=pair.getCount();
                    }
                    pair.increment();
                    exists=true;
                    logger.debug("Tolerance!");
                    break;
                }
            }
            if (!exists) {
                existingFloatValues.add(new FloatCountPair(value));
            }
        }

        conflict.clear();
        logger.debug("Resolved with "+((float)mostCommonN*100)/countTriples+"% ("+mostCommonN+"/"+countTriples+") for property: "+property);
        conflict.add(mostCommon.f.toString());

        return conflict;


    }

    static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            tolerance = Double.parseDouble(properties.getProperty("resolver.FloatModeResolver.tolerance"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
