package de.kdld16.hpi.util.rdfdatatypecomparison;

import de.kdld16.hpi.util.RDFParseTools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jonathan on 09.12.16.
 */
public class IntegerWrapper extends RDFDatatypeWrapper<Integer> {
    static String propertyName= "resolver.FloatModeResolver.tolerance";
    static double tolerance;
    static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            tolerance = Double.parseDouble(properties.getProperty(propertyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
