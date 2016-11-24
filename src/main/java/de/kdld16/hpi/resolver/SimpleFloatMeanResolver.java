package de.kdld16.hpi.resolver;


import de.kdld16.hpi.util.RDFParseTools;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public class SimpleFloatMeanResolver<T extends Number> extends Resolver {
    /*
    TODO: Describe how conflicts are resolved!
    */

    //@Override
    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {


        DescriptiveStatistics stats = new DescriptiveStatistics();

        String[] val = conflict.get(0).replaceAll("\"","").split("\\^\\^",2);
        String value= val[0];
        String datatype = val[1];
        conflict.remove(0);
        stats.addValue(Double.parseDouble(value));
        for (String rdfObject : conflict) {
            stats.addValue(RDFParseTools.parseDouble(datatype, rdfObject));
        }
        conflict.clear();
        conflict.add("\""+stats.getGeometricMean()+"^^"+datatype);
        return conflict;


    }
}
