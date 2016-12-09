package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFParseTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by jonathan on 09.12.16.
 */
public class NumericMode extends AbstractMode{
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
    public ModeResult resolve(ArrayList<RDFFact> conflict) {

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
        RDFFact mostCommonFact  = conflict.get(0);
        String[] val = mostCommonFact.getRdfObject().replaceAll("\"","").split("\\^\\^",2);
        String firstValue= val[0];
        String datatype = val[1];
        conflict.remove(0);


        FloatCountPair mostCommon= new FloatCountPair(RDFParseTools.parseDouble(datatype,firstValue));
        int mostCommonN=1;
        int countTriples = 0;
        existingFloatValues.add(mostCommon);
        for (RDFFact fact : conflict) {
            String rdfObject = fact.getRdfObject();
            countTriples++;
            boolean exists = false;
            double value = RDFParseTools.parseDouble(datatype,rdfObject);
            for (FloatCountPair pair : existingFloatValues) {
                if (Math.abs(1-(pair.f/value))<tolerance) {
                    if (pair.getCount()>=mostCommonN) {
                        mostCommon=pair;
                        mostCommonN=pair.getCount();
                        mostCommonFact=fact;
                    }
                    pair.increment();
                    exists=true;
                    break;
                }
            }
            if (!exists) {
                existingFloatValues.add(new FloatCountPair(value));
            }
        }
        if (mostCommonN>1) {
            logger.info("Resolved with "+((float)mostCommonN*100)/countTriples+"% ("+mostCommonN+"/"+countTriples+") for property: "+mostCommonFact.getRdfProperty());
        }
        return new ModeResult(mostCommonFact,mostCommonN,countTriples);
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
