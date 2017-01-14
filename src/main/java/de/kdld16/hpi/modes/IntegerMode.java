package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFParseTools;

/**
 * Created by jonathan on 09.12.16.
 */
public class IntegerMode extends NumericMode<Integer>{

    @Override
    public Integer interpretValue(String val) {
        if (rdfDatatype==null) {
        rdfDatatype = val.split("\\^\\^",2)[1];
        }
        return RDFParseTools.parseInteger(rdfDatatype,val);
    }

    @Override
    public boolean sameValue(Integer a, Integer b) {
        return Math.abs(1 - a / b.floatValue()) <= tolerance;
    }

}
