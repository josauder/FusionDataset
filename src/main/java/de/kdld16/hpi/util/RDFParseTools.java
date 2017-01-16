package de.kdld16.hpi.util;

import de.kdld16.hpi.exception.UnexpectedRDFDatatypeException;
import de.kdld16.hpi.util.rdfdatatypecomparison.DoubleWrapper;
import de.kdld16.hpi.util.rdfdatatypecomparison.IdenticalStringWrapper;
import de.kdld16.hpi.util.rdfdatatypecomparison.IntegerWrapper;
import de.kdld16.hpi.util.rdfdatatypecomparison.RDFDatatypeWrapper;
import org.dbpedia.extraction.ontology.datatypes.Datatype;

/**
 * Created by jonathan on 24.11.16.
 */


public class RDFParseTools {

    public static RDFDatatypeWrapper getRDFDatatypeWrapperDefault(String rdfProperty) throws UnexpectedRDFDatatypeException {
        if (!rdfProperty.contains("\\^\\^")) {
            return new IdenticalStringWrapper();
        }
        rdfProperty=rdfProperty.toLowerCase();
        if (rdfProperty.contains("double")) {
            return new DoubleWrapper();
        }
        if (rdfProperty.contains("integer")) {
            return new IntegerWrapper();
        }
        throw new UnexpectedRDFDatatypeException(rdfProperty);
    }

    public static int parseInteger(String rdfDatatype, String rdfObject) {
        rdfObject=rdfObject.replace(rdfDatatype,"").replaceAll("\"","").replaceAll("\\^\\^","");

        try {
            return Integer.parseInt(rdfObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double parseDouble(String rdfDatatype, String rdfObject) {
        rdfObject=rdfObject.replace(rdfDatatype,"").replaceAll("\"","").replaceAll("\\^\\^","");

        try {
            return Double.parseDouble(rdfObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }



    public static double convert(String rdfDatype, String rdfObject) {

        return 0;
    }



}
