package de.kdld16.hpi.resolver;


import de.kdld16.hpi.util.RDFParseTools;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public class SimpleIntegerMeanResolver<T extends Number> extends Resolver {
    /*
    TODO: Describe how conflicts are resolved!
    */

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {


        DescriptiveStatistics stats = new DescriptiveStatistics();
        String[] val = conflict.get(0).replaceAll("\"","").split("\\^\\^",2);
        String value= val[0];
        String datatype = val[1];
        conflict.remove(0);
        stats.addValue(Integer.parseInt(value));
        for (String rdfObject : conflict) {
            stats.addValue(RDFParseTools.parseInteger(datatype, rdfObject));
        }
        conflict.clear();
        Long mean = Math.round(stats.getGeometricMean());
        conflict.add("\""+mean+"^^"+datatype);

        logger.info("resolved with "+stats.getStandardDeviation()/mean+" as coefficient of variation");
        return conflict;


    }
}
