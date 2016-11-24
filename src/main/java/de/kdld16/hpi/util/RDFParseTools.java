package de.kdld16.hpi.util;

/**
 * Created by jonathan on 24.11.16.
 */


public class RDFParseTools {


    public static int parseInteger(String property, String rdfObject) {
        rdfObject=rdfObject.replace(property,"").replaceAll("\"","").replaceAll("\\^\\^","");

        try {
            return Integer.parseInt(rdfObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double parseDouble(String property, String rdfObject) {
        rdfObject=rdfObject.replace(property,"").replaceAll("\"","").replaceAll("\\^\\^","");

        try {
            return Double.parseDouble(rdfObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
