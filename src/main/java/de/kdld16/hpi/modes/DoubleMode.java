package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFParseTools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jonathan on 09.12.16.
 */
public class DoubleMode extends NumericMode<Double>{

    @Override
    public Double interpretValue(String val) {
        if (rdfDatatype==null) {
            rdfDatatype = val.split("\\^\\^",2)[1];
        }
        return RDFParseTools.parseDouble(rdfDatatype,val);
    }

    @Override
    public boolean sameValue(Double a, Double b) {
        return Math.abs(1 - a / b) <= tolerance;
    }

}
