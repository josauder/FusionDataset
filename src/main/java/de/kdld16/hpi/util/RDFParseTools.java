package de.kdld16.hpi.util;

/**
 * Created by jonathan on 24.11.16.
 */


public class RDFParseTools {


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
        //TODO
        return 0;
    }



}
