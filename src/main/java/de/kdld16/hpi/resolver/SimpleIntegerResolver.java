package de.kdld16.hpi.resolver;


import de.kdld16.hpi.util.RDFParseTools;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public class SimpleIntegerResolver implements AbstractResolver {
    /*
    TODO: Describe how conflicts are resolved!
    */

    @Override
    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {


        DescriptiveStatistics stats = new DescriptiveStatistics();

        for (String rdfObject : conflict) {
            stats.addValue(RDFParseTools.parseInteger(property, rdfObject));
        }
        return conflict;


    }
}
