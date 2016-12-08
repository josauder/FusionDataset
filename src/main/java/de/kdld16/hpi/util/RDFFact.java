package de.kdld16.hpi.util;

/**
 * Created by jonathan on 08.12.16.
 */
public class RDFFact {

    private String language;
    private String rdfProperty;

    public String getRdfObject() {
        return rdfObject;
    }

    public String getRdfProperty() {
        return rdfProperty;
    }


    public String getLanguage() {
        return language;
    }

    private String rdfObject;

    public RDFFact(String line) {
        String[] arr = line.split(" ",3);
        language=arr[0];
        rdfProperty=arr[1];
        rdfObject=arr[2];
    }
}
