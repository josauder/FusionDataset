package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.DBPediaHelper;
import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.util.RDFParseTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by jonathan on 09.12.16.
 */
public abstract class NumericMode<T extends Number> extends AbstractMode<T>{
    /**
        Counts most common element, returns it.
        Elements where the relative numeric value difference is smaller
        than ${resolver.FloatModeResolver.tolerance} as percentage are considered the same element
        Only one element in output.
     **/

    // statically initialized to ${resolver.FloatModeResolver.tolerance}
    static double tolerance;
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


    protected String rdfDatatype=null;

    @Override
    public String representValue(T val) {
        return val + "^^"+rdfDatatype;
    }

    public abstract boolean sameValue(T a, T b);

    @Override
    public T getKey(T in) {
        for (Map.Entry<T,ArrayList<String>> e : map.entrySet()) {
            if (sameValue(e.getKey(),in)) {
                return e.getKey();
            }
        }
        map.put(in, new ArrayList<>());
        return in;
    }

}
